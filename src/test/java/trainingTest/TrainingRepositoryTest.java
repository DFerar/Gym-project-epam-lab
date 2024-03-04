/*package trainingTest;

import com.gym.entity.TrainingEntity;
import com.gym.repository.TrainingRepository;
import com.gym.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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
        TrainingEntity training = new TrainingEntity();
        when(storage.addTraining(any(TrainingEntity.class))).thenReturn(training);

        TrainingEntity createdTraining = trainingRepository.createTraining(training);

        assertThat(createdTraining).isEqualTo(training);
    }

    @Test
    public void getTrainingByIdTest() {
        int trainingId = 1;
        TrainingEntity expectedTraining = new TrainingEntity();
        when(storage.getTrainingById(trainingId)).thenReturn(expectedTraining);

        TrainingEntity actualTraining = trainingRepository.getTrainingById(trainingId);

        assertThat(actualTraining).isEqualTo(expectedTraining);
    }

    @Test
    public void getTrainingStorageTest() {
        Set<Integer> trainingIds = new HashSet<>();
        when(storage.getTrainingIds()).thenReturn(trainingIds);

        Set<Integer> result = trainingRepository.getTrainingIds();

        assertThat(result).isEqualTo(trainingIds);
    }
}*/
