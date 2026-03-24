package com.example.cybernode.ai.CodeForge.dto.project;

import com.example.cybernode.ai.CodeForge.enums.ProjectRole;

public record InviteMemberRequest(
        String email,
        ProjectRole role
) {
}
