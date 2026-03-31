package com.example.cybernode.ai.CodeForge.service.impl;

import com.example.cybernode.ai.CodeForge.dto.auth.AuthResponse;
import com.example.cybernode.ai.CodeForge.dto.auth.LoginRequest;
import com.example.cybernode.ai.CodeForge.dto.auth.SignUpRequest;
import com.example.cybernode.ai.CodeForge.entity.User;
import com.example.cybernode.ai.CodeForge.error.BadRequestException;
import com.example.cybernode.ai.CodeForge.mapper.UserMapper;
import com.example.cybernode.ai.CodeForge.repository.UserRepository;
import com.example.cybernode.ai.CodeForge.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    UserRepository userRepository;
    UserMapper userMapper;
    @Override
    public AuthResponse signup(SignUpRequest request) {
        userRepository.findByUsername(request.username()).ifPresent(
                user ->{
                    throw new BadRequestException("User alresy exists with username: "+request.username());
                }
        );



        return null;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        return null;
    }
}
