package com.example.cybernode.ai.CodeForge.service.impl;

import com.example.cybernode.ai.CodeForge.llm.PromptUtils;
import com.example.cybernode.ai.CodeForge.llm.advisors.FileTreeContextAdvisor;
import com.example.cybernode.ai.CodeForge.llm.tools.CodeGenerationTools;
import com.example.cybernode.ai.CodeForge.security.AuthUtil;
import com.example.cybernode.ai.CodeForge.service.AiGenerationService;
import com.example.cybernode.ai.CodeForge.service.ProjectFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Map;
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


    private static final Pattern FILE_TAG_PATTERN =
            Pattern.compile("<file path=\"([^\"]+)\">(.*?)</file>", Pattern.DOTALL);
    @Override
    @PreAuthorize("@security.canEditProject(#projectId)")
    public Flux<String> streamResponse(String userMessage, Long projectId) {
        Long userId= authUtil.getCurrentUserId();

        SecurityContext securityContext = SecurityContextHolder.getContext();
        createChatSessionIfNotExists(projectId,userId);

        Map<String,Object> advisorParams=Map.of(
                "userId",userId,
                "projectId",projectId
        );

        StringBuilder fullResponseBuffer=new StringBuilder();
        CodeGenerationTools codeGenerationTools=new CodeGenerationTools(projectFileService,projectId);

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
                    fullResponseBuffer.append(content);
                })
                .doOnComplete(()-> {
                    Schedulers.boundedElastic().schedule(()-> {
                        SecurityContextHolder.setContext(securityContext); // restore
                        try {
                            parseAndSaveFiles(fullResponseBuffer.toString(), projectId);
                        } finally {
                            SecurityContextHolder.clearContext();
                        }
                    });

                })
                .doOnError(error -> log.error("Error during streaming for project id: {}",projectId))
                .map(response -> response.getResult().getOutput().getText());
    }

    private void parseAndSaveFiles(String fullResponse, Long projectId) {
        Matcher matcher=FILE_TAG_PATTERN.matcher(fullResponse);

        while(matcher.find()){
            String filePath= matcher.group(1);
            String fileContent= matcher.group(2).trim();
            projectFileService.saveFile(filePath,fileContent,projectId);
        }
    }

    private void createChatSessionIfNotExists(Long projectId, Long userId) {

    }
}
