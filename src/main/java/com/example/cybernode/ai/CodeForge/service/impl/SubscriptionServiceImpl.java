package com.example.cybernode.ai.CodeForge.service.impl;

import com.example.cybernode.ai.CodeForge.dto.subscription.CheckoutRequest;
import com.example.cybernode.ai.CodeForge.dto.subscription.CheckoutResponse;
import com.example.cybernode.ai.CodeForge.dto.subscription.PortalResponse;
import com.example.cybernode.ai.CodeForge.dto.subscription.SubscriptionResponse;
import com.example.cybernode.ai.CodeForge.service.SubscriptionService;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Override
    public SubscriptionResponse getCurrentSubscription(Long userId) {
        return null;
    }

    @Override
    public CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request, Long userId) {
        return null;
    }

    @Override
    public PortalResponse openCustomerPortal(Long userId) {
        return null;
    }
}
