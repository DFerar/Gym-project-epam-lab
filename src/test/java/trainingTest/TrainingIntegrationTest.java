package trainingTest;

import com.gym.AppConfig;
import com.gym.GymCRMFacade;
import com.gym.entities.Training;
import com.gym.entities.TrainingType;
import com.gym.storage.Storage;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
public class TrainingIntegrationTest {
    @Autowired
    private GymCRMFacade gymCRMFacade;
    @Autowired
    private Storage storage;

    @SneakyThrows
    @Test
    void trainingTest() {
        //creating training
        Training newTraining = new Training();
        newTraining.setTrainingDate("20.04.2024");
        newTraining.setTrainingDuration("2 hours");
        newTraining.setTrainingName("test_training");
        newTraining.setTrainingType(TrainingType.CARDIO);
        newTraining.setCustomerId(4);
        newTraining.setInstructorId(1);
        Training createdTraining = gymCRMFacade.createTraining(newTraining);

        assertNotNull(createdTraining);
        assertNotNull(createdTraining.getTrainingId());
        assertEquals("test_training", createdTraining.getTrainingName());

        //getTraining
        Training trainingFromBase = gymCRMFacade.getTrainingById(createdTraining.getTrainingId());
        assertNotNull(trainingFromBase);
        assertEquals(createdTraining.getTrainingName(), trainingFromBase.getTrainingName());

        //removing test training from base and checking exception
        storage.getTrainingStorage().remove(createdTraining.getTrainingId());
        storage.updateDatasource();
        assertThrows(NoSuchElementException.class, () -> gymCRMFacade.getTrainingById(createdTraining.getTrainingId()));
    }
}
