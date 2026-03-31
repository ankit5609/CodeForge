package com.example.cybernode.ai.CodeForge.dto.member;

import com.example.cybernode.ai.CodeForge.enums.ProjectRole;

import java.time.Instant;

public record MemberResponse(
        Long userId,
        String username,
        String name,
        ProjectRole projectRole,
        Instant invitedAt
) {
}
