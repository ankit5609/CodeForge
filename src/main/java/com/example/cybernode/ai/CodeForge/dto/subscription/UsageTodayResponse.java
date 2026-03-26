package com.example.cybernode.ai.CodeForge.dto.subscription;

public record UsageTodayResponse(
        Integer tokensUsed,
        Integer tokenLimit,
        Integer previousRunning,
        Integer previewsLimit
) {
}
