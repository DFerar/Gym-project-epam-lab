package com.gym.repository;

import com.gym.entity.TrainingEntity;
import com.gym.entity.TrainingType;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends JpaRepository<TrainingEntity, Long> {

    @Query("SELECT t FROM TrainingEntity t "
        + "WHERE t.customer.gymUserEntity.userName = :customerUserName "
        + "AND (cast(:fromDate as date) IS NULL OR t.trainingDate >= :fromDate)"
        + "AND (cast(:toDate as date) IS NULL OR t.trainingDate <= :toDate)"
        + "AND (:instructorName IS NULL OR t.instructor.gymUserEntity.userName = :instructorName)"
        + "AND (:trainingTypeName IS NULL OR t.trainingType.trainingTypeName = :trainingTypeName)")
    List<TrainingEntity> findTrainingsByCustomerAndCriteria(
        @Param("customerUserName") String customerUserName,
        @Param("fromDate") LocalDate fromDate,
        @Param("toDate") LocalDate toDate,
        @Param("instructorName") String instructorName,
        @Param("trainingTypeName") TrainingType trainingTypeName
    );

    @Query("SELECT t FROM TrainingEntity t "
        + "WHERE t.instructor.gymUserEntity.userName = :instructorUserName "
        + "AND (cast(:fromDate as date) IS NULL OR t.trainingDate >= :fromDate) "
        + "AND (cast(:toDate as date) IS NULL OR t.trainingDate <= :toDate) "
        + "AND (:customerName IS NULL OR t.customer.gymUserEntity.userName = :customerName)")
    List<TrainingEntity> findTrainingsByInstructorAndCriteria(
        @Param("instructorUserName") String instructorUserName,
        @Param("fromDate") LocalDate fromDate,
        @Param("toDate") LocalDate toDate,
        @Param("customerName") String customerName
    );

    void deleteTrainingEntitiesByCustomerGymUserEntityUserName(String username);
}
