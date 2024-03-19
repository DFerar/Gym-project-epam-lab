package com.gym.service;

import com.gym.entity.TrainingTypeEntity;
import com.gym.repository.TrainingTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingTypeService {
    private final TrainingTypeRepository trainingTypeRepository;

    public List<TrainingTypeEntity> getAllTrainingTypes() {
        return trainingTypeRepository.findAll();
    }
}
