package com.example.cybernode.ai.CodeForge.dto.subscription;

public record UsageTodayResponse(
        int tokensUsed,
        int tokenLimit,
        int previousRunning,
        int previewsLimit
) {
}
