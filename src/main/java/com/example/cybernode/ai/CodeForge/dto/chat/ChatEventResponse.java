package com.example.cybernode.ai.CodeForge.dto.chat;

import com.example.cybernode.ai.CodeForge.enums.ChatEventType;

public record ChatEventResponse(
        Long id,
        ChatEventType type,
        Integer sequenceOrder,
        String content,
        String filePath,
        String metadata
) {
}
