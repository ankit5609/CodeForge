package com.example.cybernode.ai.CodeForge.service;

import com.example.cybernode.ai.CodeForge.dto.deploy.DeployResponse;

public interface DeploymentService {
    DeployResponse deploy(Long projectId);
}
