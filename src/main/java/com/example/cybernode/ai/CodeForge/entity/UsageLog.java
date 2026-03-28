//package com.example.cybernode.ai.CodeForge.entity;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import lombok.*;
//import lombok.experimental.FieldDefaults;
//
//import java.time.Instant;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE)
//@Builder
//public class UsageLog {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Long id;
//
//    User user;
//
//    Project project;
//
//    String action;
//
//    Integer tokensUsed;
//
//    Integer durationMs;
//
//    String metadata; // JSON of { model_used, prompt_used}
//
//    Instant createdAt;
//}
