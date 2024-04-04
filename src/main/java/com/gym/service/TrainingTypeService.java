package com.gym.service;

import com.gym.entity.TrainingTypeEntity;
import com.gym.repository.TrainingTypeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainingTypeService {
    private final TrainingTypeRepository trainingTypeRepository;

    /**
     * Retrieves a list of all training types available in the database.
     *
     * @return List of TrainingTypeEntity objects representing all training types.
     */
    public List<TrainingTypeEntity> getAllTrainingTypes() {
        return trainingTypeRepository.findAll();
    }
}
