package com.gym.repository;

import com.gym.entity.GymUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GymUserRepository extends JpaRepository<GymUserEntity, Integer> {
    Boolean existsByUserName(String username);

    @Query("SELECT MAX(u.id) FROM GymUserEntity u")
    Integer findMaxUserId();
}
