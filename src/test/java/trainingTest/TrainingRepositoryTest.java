package trainingTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.gym.entity.TrainingEntity;
import com.gym.repository.TrainingRepository;
import com.gym.storage.Storage;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TrainingRepositoryTest {
    @Mock
    private Storage storage;

    @InjectMocks
    private TrainingRepository trainingRepository;

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
}
