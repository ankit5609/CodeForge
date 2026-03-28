//package com.example.cybernode.ai.CodeForge.entity;
//
//import com.example.cybernode.ai.CodeForge.enums.PreviewStatus;
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
//public class Preview {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Long id;
//
//    Project project;
//
//    String namespace;
//
//    String podName;
//
//    String previewUrl;
//
//    PreviewStatus status;
//
//    Instant startedAt;
//
//    Instant terminatedAt;
//
//    Instant createdAt;
//}
