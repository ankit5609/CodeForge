package com.example.cybernode.ai.CodeForge.mapper;

import com.example.cybernode.ai.CodeForge.dto.subscription.PlanResponse;
import com.example.cybernode.ai.CodeForge.dto.subscription.SubscriptionResponse;
import com.example.cybernode.ai.CodeForge.entity.Plan;
import com.example.cybernode.ai.CodeForge.entity.Subscription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlanSubscriptionMapper {

    SubscriptionResponse toSubscriptionResponse(Subscription subscription);

    PlanResponse toPlanResponse(Plan plan);

}
