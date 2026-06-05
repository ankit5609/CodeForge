package com.example.cybernode.ai.CodeForge.service.impl;

import com.example.cybernode.ai.CodeForge.dto.chat.ChatResponse;
import com.example.cybernode.ai.CodeForge.entity.ChatMessage;
import com.example.cybernode.ai.CodeForge.entity.ChatSession;
import com.example.cybernode.ai.CodeForge.entity.ChatSessionId;
import com.example.cybernode.ai.CodeForge.mapper.ChatMapper;
import com.example.cybernode.ai.CodeForge.repository.ChatMessageRepository;
import com.example.cybernode.ai.CodeForge.repository.ChatSessionRepository;
import com.example.cybernode.ai.CodeForge.security.AuthUtil;
import com.example.cybernode.ai.CodeForge.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
@Slf4j
public class ChatServiceImpl implements ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final AuthUtil authUtil;
    private final ChatSessionRepository chatSessionRepository;
    private final ChatMapper chatMapper;

    @Override
    public List<ChatResponse> getProjectChatHistory(Long projectId) {

        Long userId= authUtil.getCurrentUserId();

        ChatSession chatSession=chatSessionRepository.getReferenceById(
                new ChatSessionId(projectId,userId)
        );
        List<ChatMessage> chatMessageList=chatMessageRepository.findByChatSession(chatSession);

        return chatMapper.fromListOfChatMessage(chatMessageList);

    }
}
