package com.example.cybernode.ai.CodeForge.service.impl;

import com.example.cybernode.ai.CodeForge.dto.auth.UserProfileResponse;
import com.example.cybernode.ai.CodeForge.error.ResourceNotFoundException;
import com.example.cybernode.ai.CodeForge.repository.UserRepository;
import com.example.cybernode.ai.CodeForge.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.catalina.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level= AccessLevel.PRIVATE)
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    UserRepository userRepository;

    @Override
    public UserProfileResponse getProfile(Long userId) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                ()-> new ResourceNotFoundException("User",username)
        );
    }
}
