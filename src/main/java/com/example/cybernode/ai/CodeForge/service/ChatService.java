package com.example.cybernode.ai.CodeForge.service;


import com.example.cybernode.ai.CodeForge.dto.chat.ChatResponse;

import java.util.List;

public interface ChatService {

    List<ChatResponse> getProjectChatHistory(Long projectId);
}
