package com.gym.service;

import com.gym.entity.TrainingEntity;
import com.gym.repository.TrainingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static com.gym.utils.Utils.getLastMapObjectId;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingService {
    private final TrainingRepository trainingRepository;

    public TrainingEntity createTraining(TrainingEntity training) {
        Integer trainingId = getLastMapObjectId(trainingRepository.getTrainingIds()) + 1;
        training.setTrainingId(trainingId);
        log.info("Creating training");
        return trainingRepository.createTraining(training);
    }

    public TrainingEntity getTrainingById(Integer trainingId) {
        TrainingEntity training = trainingRepository.getTrainingById(trainingId);
        if (training != null) {
            log.info("Got training with ID: {}", trainingId);
            return training;
        } else {
            log.warn("Training with ID was not found: {}", trainingId);
            throw new NoSuchElementException("Training not found");
        }
    }
}