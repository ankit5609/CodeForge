package com.example.cybernode.ai.CodeForge.dto.subscription;

public record PlanResponse(
        Long id,

        String name,

        Integer maxProjects,

        Integer maxTokensPerDay,

        Boolean unlimitedAi,

        String price
) {
}
