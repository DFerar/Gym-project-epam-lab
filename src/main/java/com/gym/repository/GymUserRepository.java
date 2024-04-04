package com.gym.repository;

import com.gym.entity.GymUserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GymUserRepository extends JpaRepository<GymUserEntity, Long> {
    Boolean existsByUserName(String username);

    Boolean existsByUserNameAndPassword(String userName, String password);

    void deleteGymUserEntitiesByUserName(String userName);

    GymUserEntity findByUserName(String username);

    List<GymUserEntity> findGymUserEntitiesByFirstNameAndLastName(String firstName, String lastName);
}
