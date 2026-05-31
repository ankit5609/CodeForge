package com.example.cybernode.ai.CodeForge.dto.chat;

public record ChatRequest(
        String message,
        Long projectId
) {
}
