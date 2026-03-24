package com.example.cybernode.ai.CodeForge.dto.auth;

public record AuthResponse (
        String token,
        UserProfileResponse user
){
}
