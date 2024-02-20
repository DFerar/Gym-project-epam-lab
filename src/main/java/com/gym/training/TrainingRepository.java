package com.gym.training;

import com.gym.storage.Storage;
import com.gym.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;

import static com.gym.utils.Utils.*;

@Repository
@RequiredArgsConstructor
public class TrainingRepository {
    private final Storage storage;
    public Training createTraining(Training training) {
        Integer trainingId = getLastMapObjectId(storage.getTrainingStorage().keySet()) + 1;
        training.setTrainingId(trainingId);
        storage.getTrainingStorage().put(trainingId, training);
        try {
            storage.updateDatasource(storage.getStorageData());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return training;
    }

    public Training getTrainingById(Integer trainingId) {
        return storage.getTrainingStorage().get(trainingId);
    }
}
