package com.example.cybernode.ai.CodeForge.service.impl;

import com.example.cybernode.ai.CodeForge.dto.subscription.PlanLimitsResponse;
import com.example.cybernode.ai.CodeForge.dto.subscription.PlanResponse;
import com.example.cybernode.ai.CodeForge.dto.subscription.SubscriptionResponse;
import com.example.cybernode.ai.CodeForge.dto.subscription.UsageTodayResponse;
import com.example.cybernode.ai.CodeForge.entity.UsageLog;
import com.example.cybernode.ai.CodeForge.repository.UsageLogRepository;
import com.example.cybernode.ai.CodeForge.security.AuthUtil;
import com.example.cybernode.ai.CodeForge.service.SubscriptionService;
import com.example.cybernode.ai.CodeForge.service.UsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UsageServiceImpl implements UsageService {

    private final UsageLogRepository usageLogRepository;
    private final AuthUtil authUtil;
    private final SubscriptionService subscriptionService;

    @Override
    public void recordTokenUsage(Long userId, int actualToken) {
        LocalDate today = LocalDate.now();
        UsageLog todayLog=usageLogRepository.findByUserIdAndDate(userId,today)
                .orElseGet(()->createNewDailyUsageLog(userId,today));

        todayLog.setTokensUsed(todayLog.getTokensUsed()+actualToken);
        usageLogRepository.save(todayLog);
    }

    @Override
    public void checkDailyTokensUsage() {
        Long userId= authUtil.getCurrentUserId();
        SubscriptionResponse subscriptionResponse= subscriptionService.getCurrentSubscription();
        PlanResponse plan=subscriptionResponse.plan();

        LocalDate today=LocalDate.now();
        UsageLog todayLog=usageLogRepository.findByUserIdAndDate(userId,today)
                .orElseGet(()->createNewDailyUsageLog(userId,today));

        if(plan.unlimitedAi()) return;
        int currentUsage=todayLog.getTokensUsed();
        int limit=plan.maxTokensPerDay();
        if(currentUsage>=limit){
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS,
                    "Daily limit reached, Upgrade now");
        }


    }

    private UsageLog createNewDailyUsageLog(Long userId, LocalDate date){
        UsageLog newLog=UsageLog.builder()
                .userId(userId)
                .date(date)
                .tokensUsed(0)
                .build();
        return usageLogRepository.save(newLog);
    }
}
