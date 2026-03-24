package com.example.cybernode.ai.CodeForge.service;

import com.example.cybernode.ai.CodeForge.dto.subscription.CheckoutRequest;
import com.example.cybernode.ai.CodeForge.dto.subscription.CheckoutResponse;
import com.example.cybernode.ai.CodeForge.dto.subscription.PortalResponse;
import com.example.cybernode.ai.CodeForge.dto.subscription.SubscriptionResponse;
import org.jspecify.annotations.Nullable;

public interface SubscriptionService {
    SubscriptionResponse getCurrentSubscription(Long userId);

    CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request, Long userId);

    PortalResponse openCustomerPortal(Long userId);
}
