package com.example.cybernode.ai.CodeForge.dto.member;

import com.example.cybernode.ai.CodeForge.enums.ProjectRole;

public record UpdateMemberRoleRequest(
        ProjectRole role
) {
}
