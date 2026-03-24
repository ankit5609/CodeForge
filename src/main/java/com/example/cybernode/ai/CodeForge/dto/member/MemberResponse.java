package com.example.cybernode.ai.CodeForge.dto.member;

import com.example.cybernode.ai.CodeForge.enums.ProjectRole;

import java.time.Instant;

public record MemberResponse(
        Long userId,
        String email,
        String name,
        String avatarUrl,
        ProjectRole role,
        Instant invitedAt
) {
}
