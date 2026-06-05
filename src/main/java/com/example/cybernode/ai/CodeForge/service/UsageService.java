package com.example.cybernode.ai.CodeForge.service;

import com.example.cybernode.ai.CodeForge.dto.subscription.PlanLimitsResponse;
import com.example.cybernode.ai.CodeForge.dto.subscription.UsageTodayResponse;

public interface UsageService {
    void recordTokenUsage(Long userId,int actualToken);
    void checkDailyTokensUsage();
}
