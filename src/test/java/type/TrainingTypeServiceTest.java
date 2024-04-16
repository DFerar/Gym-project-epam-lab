package type;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.gym.entity.TrainingType;
import com.gym.entity.TrainingTypeEntity;
import com.gym.repository.TrainingTypeRepository;
import com.gym.service.TrainingTypeService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TrainingTypeServiceTest {
    @Mock
    private TrainingTypeRepository trainingTypeRepository;
    @InjectMocks
    private TrainingTypeService trainingTypeService;

    @Test
    public void shouldGetAllTrainingsTypes() {
        //Given
        TrainingTypeEntity trainingType1 = new TrainingTypeEntity();
        trainingType1.setId(1L);
        trainingType1.setTrainingTypeName(TrainingType.CARDIO);

        TrainingTypeEntity trainingType2 = new TrainingTypeEntity();
        trainingType2.setId(2L);
        trainingType2.setTrainingTypeName(TrainingType.TRX);

        List<TrainingTypeEntity> expectedTrainingTypes = List.of(trainingType1, trainingType2);

        when(trainingTypeRepository.findAll()).thenReturn(expectedTrainingTypes);
        //When
        List<TrainingTypeEntity> actualTrainingTypes = trainingTypeService.getAllTrainingTypes();
        //Assert
        assertThat(actualTrainingTypes).isEqualTo(expectedTrainingTypes);
    }
}
