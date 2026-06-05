package com.example.cybernode.ai.CodeForge.dto.project;

import com.example.cybernode.ai.CodeForge.enums.ProjectRole;

import java.time.Instant;

public record ProjectSummaryResponse(
        Long id,
        String name,
        Instant createdAt,
        Instant updatedAt,
        ProjectRole role
) {
}
