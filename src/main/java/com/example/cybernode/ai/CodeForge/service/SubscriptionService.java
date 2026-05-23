package com.example.cybernode.ai.CodeForge.service;

import com.example.cybernode.ai.CodeForge.dto.subscription.CheckoutRequest;
import com.example.cybernode.ai.CodeForge.dto.subscription.CheckoutResponse;
import com.example.cybernode.ai.CodeForge.dto.subscription.PortalResponse;
import com.example.cybernode.ai.CodeForge.dto.subscription.SubscriptionResponse;
import com.example.cybernode.ai.CodeForge.enums.SubscriptionStatus;
import org.jspecify.annotations.Nullable;

import java.time.Instant;

public interface SubscriptionService {
    SubscriptionResponse getCurrentSubscription();

    void activateSubscription(Long userID, Long planID, String subscriptionID,
                              String customerId, Instant periodStart, Instant periodEnd);

    void updateSubscription(String subscriptionId, SubscriptionStatus status, Instant periodStart, Instant periodEnd, Boolean cancelAtPeriodEnd, Long planId);

    void cancelSubscription(String subscriptionId);

    void renewSubscriptionPeriod(String gatewaySubscriptionId, Instant periodStart, Instant periodEnd);

    void markSubscriptionPastDue(String subId);

    boolean canCreateNewProject();
}
