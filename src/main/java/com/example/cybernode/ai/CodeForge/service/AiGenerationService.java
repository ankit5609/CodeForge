package com.example.cybernode.ai.CodeForge.service;

import com.example.cybernode.ai.CodeForge.dto.chat.StreamResponse;
import reactor.core.publisher.Flux;

import java.util.Optional;

public interface AiGenerationService {
    Flux<StreamResponse> streamResponse(String message, Long projectId);
}
