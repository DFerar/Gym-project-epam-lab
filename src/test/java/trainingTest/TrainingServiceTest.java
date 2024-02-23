package trainingTest;

import com.gym.entities.Training;
import com.gym.repositories.TrainingRepository;
import com.gym.services.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.concurrent.ConcurrentHashMap;

import static com.gym.entities.TrainingType.CARDIO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class TrainingServiceTest {
    @Mock
    private TrainingRepository trainingRepository;

    @InjectMocks
    private TrainingService trainingService;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void createInstructorTest() {
        when(trainingRepository.getTrainingStorage()).thenReturn(new ConcurrentHashMap<>());
        when(trainingRepository.createTraining(any(Training.class))).thenReturn(
                new Training(1, 1, 1,
                        "TestTraining", CARDIO, "123123", "2 hours")
        );

        Training inputTraining = new Training();
        inputTraining.setCustomerId(1);
        inputTraining.setInstructorId(1);
        inputTraining.setTrainingName("TestTraining");
        inputTraining.setTrainingType(CARDIO);
        inputTraining.setTrainingDate("123123");
        inputTraining.setTrainingDuration("2 hours");
        Training createdTraining = trainingService.createTraining(inputTraining);

        assertThat(createdTraining.getTrainingId()).isEqualTo(1);
        assertThat(createdTraining.getTrainingName()).isEqualTo("TestTraining");
    }

    @Test
    public void getTrainingByIdTest() {
        when(trainingRepository.getTrainingById(1)).thenReturn
                (new Training(1, 1, 1,
                        "TestTraining", CARDIO, "123123", "2 hours")
                );

        Training retrievedTraining = trainingRepository.getTrainingById(1);

        assertThat(retrievedTraining.getTrainingId()).isEqualTo(1);
    }
}
