package com.linkedout.userservice.repository;

import com.linkedout.userservice.entity.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, UUID> {
    boolean existsByRequesterIdAndReceiverId(String requesterId, String receiverId);
}
