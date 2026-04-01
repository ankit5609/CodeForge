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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    @Override
    public AuthResponse signup(SignUpRequest request) {
        userRepository.findByUsername(request.username()).ifPresent(
                user ->{
                    throw new BadRequestException("User already exists with username: "+request.username());
                }
        );
        User user=userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user=userRepository.save(user);


        return new AuthResponse("dummy",userMapper.toUserProfileResponse(user));
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        return null;
    }
}
