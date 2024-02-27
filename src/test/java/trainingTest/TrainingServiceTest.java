package trainingTest;

import com.gym.entity.TrainingEntity;
import com.gym.repository.TrainingRepository;
import com.gym.service.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashSet;

import static com.gym.entity.TrainingType.CARDIO;
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
    public void createTrainingTest() {
        when(trainingRepository.getTrainingIds()).thenReturn(new HashSet<>());
        when(trainingRepository.createTraining(any(TrainingEntity.class))).thenReturn(
                new TrainingEntity(1, 1, 1,
                        "TestTraining", CARDIO, "123123", "2 hours")
        );

        TrainingEntity inputTraining = new TrainingEntity();
        inputTraining.setCustomerId(1);
        inputTraining.setInstructorId(1);
        inputTraining.setTrainingName("TestTraining");
        inputTraining.setTrainingType(CARDIO);
        inputTraining.setTrainingDate("123123");
        inputTraining.setTrainingDuration("2 hours");
        TrainingEntity createdTraining = trainingService.createTraining(inputTraining);

        assertThat(createdTraining.getTrainingId()).isEqualTo(1);
        assertThat(createdTraining.getTrainingName()).isEqualTo("TestTraining");
    }

    @Test
    public void getTrainingByIdTest() {
        TrainingEntity training = new TrainingEntity(1, 1, 1,
                "TestTraining", CARDIO, "123123", "2 hours");
        when(trainingRepository.getTrainingById(training.getTrainingId())).thenReturn(training);

        TrainingEntity retrievedTraining = trainingService.getTrainingById(training.getTrainingId());

        assertThat(retrievedTraining).isEqualTo(training);
    }
}
