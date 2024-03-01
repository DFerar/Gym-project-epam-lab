package com.gym.repository;

import com.gym.entity.GymUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GymUserRepository extends JpaRepository<GymUserEntity, Integer> {
}
