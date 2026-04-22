//package com.example.cybernode.ai.CodeForge.entity;
//
//import com.example.cybernode.ai.CodeForge.enums.SubscriptionStatus;
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
//@FieldDefaults(level = AccessLevel.PRIVATE)
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class Subscription {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Long id;
//
//    User user;
//
//    Plan plan;
//
//    String stripeSubscriptionId;
//
//    SubscriptionStatus status;
//
//    Instant currentPeriodStart;
//
//    Instant currentPeriodEnd;
//
//    Boolean cancelAtPeriodEnd;
//
//    Instant createdAt;
//
//    Instant updatedAt;
//}
