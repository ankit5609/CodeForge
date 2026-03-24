package com.example.cybernode.ai.CodeForge.service;

import com.example.cybernode.ai.CodeForge.dto.auth.AuthResponse;
import com.example.cybernode.ai.CodeForge.dto.auth.LoginRequest;
import com.example.cybernode.ai.CodeForge.dto.auth.SignUpRequest;
import org.jspecify.annotations.Nullable;

public interface AuthService {
     AuthResponse signup(SignUpRequest request);
     AuthResponse login(LoginRequest request);
}
