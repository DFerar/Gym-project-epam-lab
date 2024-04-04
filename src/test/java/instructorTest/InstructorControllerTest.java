package instructorTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.gym.controller.InstructorController;
import com.gym.entity.GymUserEntity;
import com.gym.entity.InstructorEntity;
import com.gym.mapper.InstructorMapper;
import com.gym.requestDto.instructorRequest.CreateInstructorRequestDto;
import com.gym.requestDto.instructorRequest.UpdateInstructorProfileRequestDto;
import com.gym.responseDto.instructorResponse.CreateInstructorResponseDto;
import com.gym.responseDto.instructorResponse.GetInstructorProfileResponseDto;
import com.gym.responseDto.instructorResponse.GetNotAssignedOnCustomerInstructorsResponseDto;
import com.gym.responseDto.instructorResponse.UpdateInstructorProfileResponseDto;
import com.gym.service.AuthenticationService;
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

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private InstructorController instructorController;

    @Test
    public void shouldCreateInstructor() {
        // Given
        CreateInstructorRequestDto requestDto = new CreateInstructorRequestDto();
        GymUserEntity userEntity = new GymUserEntity();
        InstructorEntity instructorEntity = new InstructorEntity();
        when(instructorMapper.mapCreateInstructorRequestDtoToUserEntity(requestDto)).thenReturn(userEntity);
        when(instructorService.createInstructor(userEntity, requestDto.getSpecialization()))
            .thenReturn(instructorEntity);
        when(instructorMapper.mapToResponseDto(instructorEntity.getGymUserEntity())).thenReturn(
            new CreateInstructorResponseDto());

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
        String loginUserName = RandomStringUtils.randomAlphabetic(7);
        String loginPassword = RandomStringUtils.randomAlphabetic(7);
        InstructorEntity instructorEntity = new InstructorEntity();

        when(instructorService.getInstructorByUsername(username)).thenReturn(instructorEntity);
        when(instructorMapper.mapInstructorEntityToGetInstructorResponseDto(instructorEntity)).thenReturn(
            new GetInstructorProfileResponseDto());

        // When
        ResponseEntity<GetInstructorProfileResponseDto> response = instructorController.getInstructor(username,
            loginUserName, loginPassword);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void shouldUpdateInstructor() {
        // Given
        UpdateInstructorProfileRequestDto newData = new UpdateInstructorProfileRequestDto();
        String loginUserName = RandomStringUtils.randomAlphabetic(7);
        String loginPassword = RandomStringUtils.randomAlphabetic(7);
        GymUserEntity gymUserEntityFromNewData = new GymUserEntity();
        InstructorEntity instructorEntity = new InstructorEntity();

        when(instructorMapper.mapUpdateInstructorRequestDtoToUserEntity(newData)).thenReturn(gymUserEntityFromNewData);
        when(instructorService.updateInstructor(gymUserEntityFromNewData, newData.getSpecialization()))
            .thenReturn(instructorEntity);
        when(instructorMapper.mapInstructorEntityToUpdateInstructorResponseDto(instructorEntity))
            .thenReturn(new UpdateInstructorProfileResponseDto());

        // When
        ResponseEntity<UpdateInstructorProfileResponseDto> response = instructorController.updateInstructor(newData,
            loginUserName, loginPassword);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void shouldGetNotAssignedInstructors() {
        // Given
        String username = RandomStringUtils.randomAlphabetic(7);
        String loginUserName = RandomStringUtils.randomAlphabetic(7);
        String loginPassword = RandomStringUtils.randomAlphabetic(7);
        List<InstructorEntity> instructorEntities = Collections.emptyList();

        when(instructorService.getInstructorsNotAssignedToCustomerByCustomerUserName(username))
            .thenReturn(instructorEntities);
        when(instructorMapper.mapInstructorEntitiesToInstructorDtos(instructorEntities))
            .thenReturn(Collections.emptyList());

        // When
        ResponseEntity<List<GetNotAssignedOnCustomerInstructorsResponseDto>> response =
            instructorController.getNotAssignedInstructors(username, loginUserName, loginPassword);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void shouldChangeInstructorActivity() {
        // Given
        String username = RandomStringUtils.randomAlphabetic(7);
        String loginUserName = RandomStringUtils.randomAlphabetic(7);
        String loginPassword = RandomStringUtils.randomAlphabetic(7);

        // When
        ResponseEntity<String> response = instructorController.instructorActivation(
            username, loginUserName, loginPassword);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
