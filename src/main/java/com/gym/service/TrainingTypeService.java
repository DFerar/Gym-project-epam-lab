package com.gym.service;

import com.gym.entity.TrainingTypeEntity;
import com.gym.repository.TrainingTypeRepository;
import com.gym.responseDto.trainingResponse.TrainingTypeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingTypeService {
    private final TrainingTypeRepository trainingTypeRepository;

    public List<TrainingTypeResponseDto> getAllTrainingTypes() {
        return trainingTypeRepository.findAll().stream()
                .map(this::trainingTypeEntityToTrainingTypeDto)
                .toList();
    }

    private TrainingTypeResponseDto trainingTypeEntityToTrainingTypeDto(TrainingTypeEntity trainingTypeEntity) {
        return new TrainingTypeResponseDto(trainingTypeEntity.getId(), trainingTypeEntity.getTrainingTypeName());
    }
}
