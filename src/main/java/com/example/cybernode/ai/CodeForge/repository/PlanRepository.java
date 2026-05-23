package com.example.cybernode.ai.CodeForge.repository;

import com.example.cybernode.ai.CodeForge.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Plan,Long> {
    Optional<Plan> findByStripePriceId(String id);
}
