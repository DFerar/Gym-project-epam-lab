package com.gym.repositories;

import com.gym.entities.Training;
import com.gym.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class TrainingRepository {
    private final Storage storage;

    @SneakyThrows
    public Training createTraining(Training training) {
        storage.getTrainingStorage().put(training.getTrainingId(), training);
        storage.updateDatasource();
        return training;
    }

    public Training getTrainingById(Integer trainingId) {
        return storage.getTrainingStorage().get(trainingId);
    }

    public Map<Integer, Training> getTrainingStorage() {
        return storage.getTrainingStorage();
    }
}
