package com.example.cybernode.ai.CodeForge.dto.auth;

public record SignUpRequest(
        String name,
        String email,
        String password
) {
}
