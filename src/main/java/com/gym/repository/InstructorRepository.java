package com.gym.repository;

import com.gym.entity.InstructorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructorRepository extends JpaRepository<InstructorEntity, Integer> {
    InstructorEntity findInstructorEntityByGymUserEntity_UserName(String userName);

    @Query("SELECT t FROM InstructorEntity t " +
            "WHERE NOT EXISTS " +
            "(SELECT c FROM CustomerEntity c " +
            "WHERE c.gymUserEntity.userName = :customerUsername " +
            "AND t MEMBER OF c.instructors)")
    List<InstructorEntity> findUnassignedInstructorsByCustomerUsername(
            @Param("customerUsername") String customerUsername
    );

    boolean existsByGymUserEntity_UserNameAndGymUserEntity_Password(String userName, String password);
}
