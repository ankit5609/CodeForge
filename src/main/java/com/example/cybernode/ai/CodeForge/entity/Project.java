package com.example.cybernode.ai.CodeForge.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @ManyToOne
    @JoinColumn(name="owner_id", nullable = false)
    private User owner;

    @Setter
    private Boolean isPublic;

    private Instant createdAt;

    private Instant updatedAt;
    @Setter
    private Instant deletedAt;

    @PrePersist
    void setCreatedAt(){
        this.createdAt=Instant.now();
        this.updatedAt=Instant.now();
    }
    @PreUpdate
    void setUpdatedAt(){
        this.updatedAt=Instant.now();
    }
}
