package com.example.cybernode.ai.CodeForge.dto.project;

import com.example.cybernode.ai.CodeForge.dto.auth.UserProfileResponse;

import java.time.Instant;

public record ProjectResponse(
        Long id,
        String name,
        Instant createdAt,
        Instant updatedAt,
        UserProfileResponse userProfileResponse
) {

}
