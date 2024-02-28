package com.gym.repository;

import com.gym.entity.TrainingEntity;
import com.gym.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@RequiredArgsConstructor
public class TrainingRepository {
    private final Storage storage;

    @SneakyThrows
    public TrainingEntity createTraining(TrainingEntity training) {
        TrainingEntity trainingFromBase = storage.addTraining(training);
        storage.updateDatasource();
        return trainingFromBase;
    }

    public TrainingEntity getTrainingById(Integer trainingId) {
        return storage.getTrainingById(trainingId);
    }

    public Set<Integer> getTrainingIds() {
        return storage.getTrainingIds();
    }
}
