package com.linkedout.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "connections")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Connection {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String requestedId;

    @Column(nullable = false)
    private String receiverId;

    @Enumerated(EnumType.STRING)
    private ConnectionStatus connectionStatus;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
