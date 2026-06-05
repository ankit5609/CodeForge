package com.example.cybernode.ai.CodeForge.dto.chat;

import com.example.cybernode.ai.CodeForge.entity.ChatSession;
import com.example.cybernode.ai.CodeForge.enums.MessageRole;

import java.time.Instant;
import java.util.List;

public record ChatResponse(

        Long id,
        MessageRole role,
        List<ChatEventResponse> events,
        String content,
        Integer tokensUsed,
        Instant createdAt

) {

}
