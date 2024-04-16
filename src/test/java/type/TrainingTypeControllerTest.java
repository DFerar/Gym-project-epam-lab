package type;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.gym.controller.TrainingTypeController;
import com.gym.dto.response.training.TrainingTypeResponseDto;
import com.gym.entity.TrainingTypeEntity;
import com.gym.mapper.TrainingMapper;
import com.gym.service.TrainingTypeService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class TrainingTypeControllerTest {
    @Mock
    private TrainingTypeService trainingTypeService;

    @Mock
    private TrainingMapper trainingMapper;

    @InjectMocks
    private TrainingTypeController trainingTypeController;

    @Test
    public void shouldGetAllTrainingTypes() {
        // Given

        List<TrainingTypeEntity> trainingTypeEntities = Collections.singletonList(new TrainingTypeEntity());
        when(trainingTypeService.getAllTrainingTypes()).thenReturn(trainingTypeEntities);
        List<TrainingTypeResponseDto> trainingTypeResponseDtos =
            Collections.singletonList(new TrainingTypeResponseDto());
        when(trainingMapper.mapTrainingTypeEntitiesToTrainingTypeResponseDto(trainingTypeEntities)).thenReturn(
            trainingTypeResponseDtos);

        // When
        ResponseEntity<List<TrainingTypeResponseDto>> response =
            trainingTypeController.getAllTrainingTypes();

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(trainingTypeResponseDtos);
    }
}
