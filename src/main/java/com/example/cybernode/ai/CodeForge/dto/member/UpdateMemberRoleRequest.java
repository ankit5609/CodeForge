package com.example.cybernode.ai.CodeForge.dto.member;

import com.example.cybernode.ai.CodeForge.enums.ProjectRole;
import jakarta.validation.constraints.NotNull;

public record UpdateMemberRoleRequest(
        @NotNull ProjectRole role
) {
}
