package com.example.cybernode.ai.CodeForge.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    @Setter
    private String username;
    @Setter
    private String password;
    @Setter
    private String name;

    @Column(unique = true)
    String stripeCustomerId;

    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
