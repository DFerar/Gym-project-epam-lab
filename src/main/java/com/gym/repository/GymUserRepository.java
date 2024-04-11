package com.gym.repository;

import com.gym.entity.GymUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GymUserRepository extends JpaRepository<GymUserEntity, Long> {
    Boolean existsByUserName(String username);

    void deleteGymUserEntitiesByUserName(String userName);

    GymUserEntity findByUserName(String username);

    @Query("SELECT MAX(u.userIndex) FROM GymUserEntity u WHERE u.firstName = :firstName AND u.lastName = :lastName")
    Integer findMaxUserIndexByFirstNameAndLastName(@Param("firstName") String firstName,
                                                   @Param("lastName") String lastName);
}
