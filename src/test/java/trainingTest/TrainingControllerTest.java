package trainingTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.gym.controller.TrainingController;
import com.gym.mapper.TrainingMapper;
import com.gym.requestDto.customerRequest.GetCustomerTrainingListRequestDto;
import com.gym.requestDto.instructorRequest.GetInstructorTrainingsRequestDto;
import com.gym.requestDto.trainingRequest.CreateTrainingRequestDto;
import com.gym.responseDto.trainingResponse.CustomerTrainingsResponseDto;
import com.gym.responseDto.trainingResponse.InstructorTrainingsResponseDto;
import com.gym.service.AuthenticationService;
import com.gym.service.TrainingService;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class TrainingControllerTest {
    @Mock
    private TrainingService trainingService;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private TrainingMapper trainingMapper;

    @InjectMocks
    private TrainingController trainingController;

    @Test
    public void shouldGetCustomerTrainings() {
        // Given
        String username = RandomStringUtils.randomAlphabetic(7);
        String password = RandomStringUtils.randomAlphabetic(7);
        GetCustomerTrainingListRequestDto requestDto = new GetCustomerTrainingListRequestDto();

        when(trainingMapper.mapCustomerTrainingEntitiesToTrainingDtos(anyList())).thenReturn(Collections.emptyList());

        // When
        ResponseEntity<List<CustomerTrainingsResponseDto>> response =
            trainingController.getCustomerTrainings(requestDto,
                username, password);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void shouldGetInstructorTrainings() {
        // Given
        String username = RandomStringUtils.randomAlphabetic(7);
        String password = RandomStringUtils.randomAlphabetic(7);
        GetInstructorTrainingsRequestDto requestDto = new GetInstructorTrainingsRequestDto();

        when(trainingService.getInstructorListOfTrainings(
            eq(requestDto.getUserName()),
            eq(null),
            eq(null),
            eq(requestDto.getCustomerName()))).thenReturn(Collections.emptyList());
        when(trainingMapper.mapInstructorTrainingEntitiesToTrainingDtos(anyList())).thenReturn(Collections.emptyList());

        // When
        ResponseEntity<List<InstructorTrainingsResponseDto>> response = trainingController.getInstructorTrainings(
            requestDto, username, password);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void shouldCreateTraining() {
        // Given
        String username = RandomStringUtils.randomAlphabetic(7);
        String password = RandomStringUtils.randomAlphabetic(7);
        CreateTrainingRequestDto requestDto = new CreateTrainingRequestDto();

        // When
        ResponseEntity<String> response = trainingController.createTraining(requestDto,
            username, password);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}
