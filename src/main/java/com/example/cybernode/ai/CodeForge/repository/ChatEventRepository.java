package com.example.cybernode.ai.CodeForge.repository;

import com.example.cybernode.ai.CodeForge.entity.ChatEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatEventRepository extends JpaRepository<ChatEvent,Long> {
}
