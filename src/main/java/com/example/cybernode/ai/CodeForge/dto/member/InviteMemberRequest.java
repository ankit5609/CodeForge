package com.example.cybernode.ai.CodeForge.dto.member;

import com.example.cybernode.ai.CodeForge.enums.ProjectRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InviteMemberRequest(
        @NotBlank String username,
        @NotNull ProjectRole role
) {
}
