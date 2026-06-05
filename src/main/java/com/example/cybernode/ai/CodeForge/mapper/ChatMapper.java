package com.example.cybernode.ai.CodeForge.mapper;

import com.example.cybernode.ai.CodeForge.dto.chat.ChatEventResponse;
import com.example.cybernode.ai.CodeForge.dto.chat.ChatResponse;
import com.example.cybernode.ai.CodeForge.entity.ChatEvent;
import com.example.cybernode.ai.CodeForge.entity.ChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    @Mapping(source = "chatEventType", target = "type")
    ChatEventResponse fromChatEvent(ChatEvent chatEvent);
    List<ChatResponse> fromListOfChatMessage(List<ChatMessage> chatMessageList);
}
