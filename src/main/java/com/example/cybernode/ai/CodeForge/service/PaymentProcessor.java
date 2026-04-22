package com.example.cybernode.ai.CodeForge.service;

import com.example.cybernode.ai.CodeForge.dto.subscription.CheckoutRequest;
import com.example.cybernode.ai.CodeForge.dto.subscription.CheckoutResponse;
import com.example.cybernode.ai.CodeForge.dto.subscription.PortalResponse;
import com.stripe.model.StripeObject;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface PaymentProcessor {

    CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request);

    PortalResponse openCustomerPortal();

    void handleWebhookEvent(String type, StripeObject stripeObject, Map<String, String> metadata);
}
