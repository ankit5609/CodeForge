package com.example.cybernode.ai.CodeForge.service.impl;

import com.example.cybernode.ai.CodeForge.dto.chat.StreamResponse;
import com.example.cybernode.ai.CodeForge.entity.*;
import com.example.cybernode.ai.CodeForge.enums.ChatEventType;
import com.example.cybernode.ai.CodeForge.enums.MessageRole;
import com.example.cybernode.ai.CodeForge.error.ResourceNotFoundException;
import com.example.cybernode.ai.CodeForge.llm.LlmResponseParser;
import com.example.cybernode.ai.CodeForge.llm.PromptUtils;
import com.example.cybernode.ai.CodeForge.llm.advisors.FileTreeContextAdvisor;
import com.example.cybernode.ai.CodeForge.llm.tools.CodeGenerationTools;
import com.example.cybernode.ai.CodeForge.repository.*;
import com.example.cybernode.ai.CodeForge.security.AuthUtil;
import com.example.cybernode.ai.CodeForge.service.AiGenerationService;
import com.example.cybernode.ai.CodeForge.service.ProjectFileService;
import com.example.cybernode.ai.CodeForge.service.UsageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiGenerationServiceImpl implements AiGenerationService {

    private final ChatClient chatClient;
    private final AuthUtil authUtil;
    private final ProjectFileService projectFileService;
    private final FileTreeContextAdvisor fileTreeContextAdvisor;
    private final LlmResponseParser llmResponseParser;
    private final ChatSessionRepository chatSessionRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatEventRepository chatEventRepository;
    private final UsageLogRepository usageLogRepository;
    private final UsageService usageService;


    private static final Pattern FILE_TAG_PATTERN =
            Pattern.compile("<file path=\"([^\"]+)\">(.*?)</file>", Pattern.DOTALL);
    @Override
    @PreAuthorize("@security.canEditProject(#projectId)")
    public Flux<StreamResponse> streamResponse(String userMessage, Long projectId) {
//        usageService.checkDailyTokensUsage();
        Long userId= authUtil.getCurrentUserId();

        SecurityContext securityContext = SecurityContextHolder.getContext();
        ChatSession chatSession=createChatSessionIfNotExists(projectId,userId);

        Map<String,Object> advisorParams=Map.of(
                "userId",userId,
                "projectId",projectId
        );

        StringBuilder fullResponseBuffer=new StringBuilder();
        CodeGenerationTools codeGenerationTools=new CodeGenerationTools(projectFileService,projectId);

        AtomicReference<Long>  startTime=new AtomicReference<>(System.currentTimeMillis());
        AtomicReference<Long>  endTime=new AtomicReference<>(0L);
        AtomicReference<Usage> usageRef = new AtomicReference<>();

        return chatClient.prompt()
                .system(PromptUtils.CODE_GENERATION_SYSTEM_PROMPT)
                .user(userMessage)
                .tools(codeGenerationTools)
                .advisors(advisorSpec -> {
                    advisorSpec.params(advisorParams);
                    advisorSpec.advisors(fileTreeContextAdvisor);
                        }
                )
                .stream()
                .chatResponse()
                .doOnNext(response ->{

                    String content=response.getResult().getOutput().getText();
                    if(content!=null && !content.isEmpty() && endTime.get()==0){
                        endTime.set(System.currentTimeMillis());
                    }
                    if(response.getMetadata().getUsage() != null) {
                        usageRef.set(response.getMetadata().getUsage());
                    }
                    fullResponseBuffer.append(content);

                })
                .doOnComplete(()-> {
                    Schedulers.boundedElastic().schedule(()-> {
                        SecurityContextHolder.setContext(securityContext); // restore
                        try {
//                            parseAndSaveFiles(fullResponseBuffer.toString(), projectId);
                            Long duration=(endTime.get()-startTime.get())/1000;
                            finalizeChats(userMessage,chatSession, fullResponseBuffer.toString(),projectId,duration,usageRef.get());
                        } finally {
                            SecurityContextHolder.clearContext();
                        }
                    });

                })
                .doOnError(error -> log.error("Error during streaming for project id: {}",projectId))
                .map(response -> {
                        String text=response.getResult().getOutput().getText();
                        return new StreamResponse(text!=null ? text:"");
                });
    }

    private void finalizeChats(String userMessage, ChatSession chatSession,String fullText, Long projectId, Long duration, Usage usage){
        if(usage != null) {
            int totalTokens = usage.getTotalTokens();
            usageService.recordTokenUsage(chatSession.getUser().getId(), totalTokens);
        }
        //Save the User message
        chatMessageRepository.save(
                ChatMessage.builder()
                        .chatSession(chatSession)
                        .role(MessageRole.USER)
                        .content(userMessage)
                        .tokensUsed(usage.getPromptTokens())
                .build()
        );

        //
        ChatMessage assistantChatMessage=ChatMessage.builder()
                .chatSession(chatSession)
                .role(MessageRole.ASSISTANT)
                .content(fullText)
                .tokensUsed(usage.getCompletionTokens())
                .build();
        assistantChatMessage=chatMessageRepository.save(assistantChatMessage);

        List<ChatEvent> chatEventList= llmResponseParser.parseChatEvents(fullText,assistantChatMessage);

        chatEventList.add(0,ChatEvent.builder()
                        .chatEventType(ChatEventType.THOUGHT)
                        .chatMessage(assistantChatMessage)
                        .content("Thought for "+duration+"s")
                        .sequenceOrder(0)
                .build());

        chatEventList.stream()
                .filter(e-> e.getChatEventType()== ChatEventType.FILE_EDIT)
                .forEach(e-> projectFileService.saveFile(e.getFilePath(), e.getContent(),projectId));

        chatEventRepository.saveAll(chatEventList);
    }

//    private void parseAndSaveFiles(String fullResponse, Long projectId) {
//        Matcher matcher=FILE_TAG_PATTERN.matcher(fullResponse);
//
//        while(matcher.find()){
//            String filePath= matcher.group(1);
//            String fileContent= matcher.group(2).trim();
//            projectFileService.saveFile(filePath,fileContent,projectId);
//        }
//    }
    private ChatSession createChatSessionIfNotExists(Long projectId, Long userId) {
        ChatSessionId chatSessionId=new ChatSessionId(projectId,userId);
        ChatSession chatSession=chatSessionRepository.findById(chatSessionId).orElse(null);

        if(chatSession==null){
            Project project=projectRepository.findById(projectId).orElseThrow(
                    ()-> new ResourceNotFoundException("project",projectId.toString())
            );
            User user=userRepository.findById(userId).orElseThrow(
                    ()-> new ResourceNotFoundException("user",userId.toString())
            );

            chatSession=ChatSession.builder()
                    .id(chatSessionId)
                    .project(project)
                    .user(user)
                    .build();

            chatSession=chatSessionRepository.save(chatSession);

        }
        return chatSession;
    }
}
