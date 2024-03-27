package com.gym.repository;

import com.gym.entity.GymUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GymUserRepository extends JpaRepository<GymUserEntity, Long> {
    Boolean existsByUserName(String username);

    Boolean existsByUserNameAndPassword(String userName, String password);

    @Query("SELECT coalesce(MAX(u.id), 1L) FROM GymUserEntity u")
    Long findMaxUserId();

    void deleteGymUserEntitiesByUserName(String userName);

    GymUserEntity findByUserName(String username);
}
