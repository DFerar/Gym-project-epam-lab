package com.gym.repository;

import com.gym.entity.TrainingType;
import com.gym.entity.TrainingTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingTypeRepository extends JpaRepository<TrainingTypeEntity, Long> {
    TrainingTypeEntity findByTrainingTypeName(TrainingType trainingTypeName);
}
