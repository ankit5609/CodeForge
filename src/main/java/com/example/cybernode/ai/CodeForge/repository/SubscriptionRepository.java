package com.example.cybernode.ai.CodeForge.repository;

import com.example.cybernode.ai.CodeForge.entity.Subscription;
import com.example.cybernode.ai.CodeForge.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.Set;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {

    /*
    * Get the current active Subscription
     */
    Optional<Subscription> findByUserIdAndStatusIn(Long userId, Set<SubscriptionStatus> statusSet);

    boolean existsByStripeSubscriptionId(String subscriptionID);

    Optional<Subscription> findByStripeSubscriptionId(String gatewaySubscriptionId);
}
