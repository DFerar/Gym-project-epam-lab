package com.gym.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "blocked_users")
public class BlockedUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blocked_users_id_gen")
    @SequenceGenerator(name = "blocked_users_id_gen", sequenceName = "blocked_user_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", length = Integer.MAX_VALUE)
    private String username;

    @Column(name = "timestamp_of_blocking")
    private LocalDateTime timestampOfBlocking;

    @Column(name = "timestamp_of_unblocking")
    private LocalDateTime timestampOfUnblocking;
}