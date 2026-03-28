package com.example.cybernode.ai.CodeForge.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class ProjectMemberId {
    Long userId;
    Long projectId;
}
