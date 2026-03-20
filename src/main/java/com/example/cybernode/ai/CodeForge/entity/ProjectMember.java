package com.example.cybernode.ai.CodeForge.entity;

import com.example.cybernode.ai.CodeForge.enums.ProjectRole;
import jakarta.persistence.Entity;
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
public class ProjectMember {

    @Id
    ProjectMemberId id;

    Project project;

    User user;

    ProjectRole role;

    Instant invitedAt;

    Instant acceptedAt;

}
