package instructor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.gym.controller.InstructorController;
import com.gym.dto.request.instructor.CreateInstructorRequestDto;
import com.gym.dto.request.instructor.UpdateInstructorProfileRequestDto;
import com.gym.dto.response.instructor.CreateInstructorResponseDto;
import com.gym.dto.response.instructor.GetInstructorProfileResponseDto;
import com.gym.dto.response.instructor.GetNotAssignedOnCustomerInstructorsResponseDto;
import com.gym.dto.response.instructor.UpdateInstructorProfileResponseDto;
import com.gym.entity.GymUserEntity;
import com.gym.entity.InstructorEntity;
import com.gym.mapper.InstructorMapper;
import com.gym.service.InstructorService;
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
public class InstructorControllerTest {
    @Mock
    private InstructorService instructorService;

    @Mock
    private InstructorMapper instructorMapper;

    @InjectMocks
    private InstructorController instructorController;

    @Test
    public void shouldCreateInstructor() {
        // Given
        CreateInstructorRequestDto requestDto = new CreateInstructorRequestDto();
        GymUserEntity userEntity = new GymUserEntity();
        InstructorEntity instructorEntity = new InstructorEntity();
        when(instructorMapper.mapCreateInstructorRequestDtoToUserEntity(requestDto)).thenReturn(userEntity);
        when(instructorService.createInstructor(userEntity, requestDto.getSpecialization())).thenReturn(
            instructorEntity);
        when(instructorMapper.mapToResponseDto(instructorEntity.getGymUserEntity(),
            userEntity.getPassword())).thenReturn(new CreateInstructorResponseDto());

        // When
        ResponseEntity<CreateInstructorResponseDto> response = instructorController.createInstructor(requestDto);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void shouldGetInstructor() {
        // Given
        String username = RandomStringUtils.randomAlphabetic(7);
        InstructorEntity instructorEntity = new InstructorEntity();

        when(instructorService.getInstructorByUsername(username)).thenReturn(instructorEntity);
        when(instructorMapper.mapInstructorEntityToGetInstructorResponseDto(instructorEntity)).thenReturn(
            new GetInstructorProfileResponseDto());

        // When
        ResponseEntity<GetInstructorProfileResponseDto> response =
            instructorController.getInstructor(username);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void shouldUpdateInstructor() {
        // Given
        UpdateInstructorProfileRequestDto newData = new UpdateInstructorProfileRequestDto();
        GymUserEntity gymUserEntityFromNewData = new GymUserEntity();
        InstructorEntity instructorEntity = new InstructorEntity();

        when(instructorMapper.mapUpdateInstructorRequestDtoToUserEntity(newData)).thenReturn(gymUserEntityFromNewData);
        when(instructorService.updateInstructor(gymUserEntityFromNewData, newData.getSpecialization())).thenReturn(
            instructorEntity);
        when(instructorMapper.mapInstructorEntityToUpdateInstructorResponseDto(instructorEntity)).thenReturn(
            new UpdateInstructorProfileResponseDto());

        // When
        ResponseEntity<UpdateInstructorProfileResponseDto> response =
            instructorController.updateInstructor(newData);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void shouldGetNotAssignedInstructors() {
        // Given
        String username = RandomStringUtils.randomAlphabetic(7);
        String loginPassword = RandomStringUtils.randomAlphabetic(7);
        List<InstructorEntity> instructorEntities = Collections.emptyList();

        when(instructorMapper.mapInstructorEntitiesToInstructorDtos(instructorEntities)).thenReturn(
            Collections.emptyList());

        // When
        ResponseEntity<List<GetNotAssignedOnCustomerInstructorsResponseDto>> response =
            instructorController.getNotAssignedInstructors(loginPassword);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void shouldChangeInstructorActivity() {
        // Given
        String loginPassword = RandomStringUtils.randomAlphabetic(7);

        // When
        ResponseEntity<String> response =
            instructorController.instructorActivation(loginPassword);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
