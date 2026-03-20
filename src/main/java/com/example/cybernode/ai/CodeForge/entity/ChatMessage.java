package com.example.cybernode.ai.CodeForge.entity;

import com.example.cybernode.ai.CodeForge.enums.ChatRole;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    ChatSession chatSession;

    ChatRole role;

    String content;

    String toolCalls;  // JSON array of tools called

    Integer tokensUsed;

    Instant createdAt;

}
