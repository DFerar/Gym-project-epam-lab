package com.gym.repository;

import com.gym.entity.TrainingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<TrainingEntity, Integer> {

    @Query("SELECT t FROM TrainingEntity t " +
            "WHERE t.customer.id = :customerId " +
            "AND (:fromDate IS NULL OR t.trainingDate >= :fromDate)" +
            "AND (:toDate IS NULL OR t.trainingDate <= :toDate)" +
            "AND (:instructorName IS NULL OR t.instructor.gymUserEntity.userName = :instructorName)" +
            "AND (:trainingTypeName IS NULL OR t.trainingType.trainingTypeName = :trainingTypeName)")
    List<TrainingEntity> findTrainingsByCustomerAndCriteria(
            @Param("customerId") Integer customerId,
            @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate,
            @Param("instructorName") String instructorName,
            @Param("trainingTypeName") String trainingTypeName
    );

    @Query("SELECT t FROM TrainingEntity t " +
            "WHERE t.instructor.id = :instructorId " +
            "AND (:fromDate IS NULL OR t.trainingDate >= :fromDate) " +
            "AND (:toDate IS NULL OR t.trainingDate <= :toDate) " +
            "AND (:customerName IS NULL OR t.customer.gymUserEntity.userName = :customerName)")
    List<TrainingEntity> findTrainingsByInstructorAndCriteria(
            @Param("instructorId") Integer instructorId,
            @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate,
            @Param("customerName") String customerName
    );

    void deleteTrainingEntitiesByCustomer_GymUserEntity_UserName(String username);
}
