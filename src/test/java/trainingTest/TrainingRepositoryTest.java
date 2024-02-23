package trainingTest;

import com.gym.entities.Training;
import com.gym.entities.TrainingType;
import com.gym.repositories.TrainingRepository;
import com.gym.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

public class TrainingRepositoryTest {
    @Mock
    private Storage storage;

    @InjectMocks
    private TrainingRepository trainingRepository;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void createTrainingTest() {
        Map<Integer, Training> trainingStorage = new ConcurrentHashMap<>();
        Mockito.when(storage.getTrainingStorage()).thenReturn(trainingStorage);

        Training newTraining = new Training();
        newTraining.setTrainingId(1);
        newTraining.setTrainingDate("213123123");
        newTraining.setTrainingDuration("2 hours");
        newTraining.setTrainingName("kek");
        newTraining.setTrainingType(TrainingType.CARDIO);
        newTraining.setCustomerId(4);
        newTraining.setInstructorId(1);
        Training createdTraining = trainingRepository.createTraining(newTraining);

        assertThat(createdTraining).isEqualTo(newTraining);
        assertThat(trainingStorage.containsKey(1)).isTrue();
    }

    @Test
    public void getTrainingByIdTest() {
        Map<Integer, Training> trainingStorage = new ConcurrentHashMap<>();
        trainingStorage.put(1, new Training());
        Mockito.when(storage.getTrainingStorage()).thenReturn(trainingStorage);

        Training retrievedTraining = trainingRepository.getTrainingById(1);

        assertThat(retrievedTraining).isEqualTo(trainingStorage.get(1));
    }

    @Test
    public void getTrainingStorageTest() {
        Map<Integer, Training> trainingStorage = new ConcurrentHashMap<>();
        trainingStorage.put(1, new Training());
        Mockito.when(storage.getTrainingStorage()).thenReturn(trainingStorage);

        Map<Integer, Training> result = trainingRepository.getTrainingStorage();

        assertThat(result).isEqualTo(trainingStorage);
    }
}
