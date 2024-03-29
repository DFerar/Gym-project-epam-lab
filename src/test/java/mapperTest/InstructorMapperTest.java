package mapperTest;

import com.gym.entity.GymUserEntity;
import com.gym.entity.InstructorEntity;
import com.gym.entity.TrainingType;
import com.gym.entity.TrainingTypeEntity;
import com.gym.mapper.InstructorMapper;
import com.gym.requestDto.instructorRequest.CreateInstructorRequestDto;
import com.gym.requestDto.instructorRequest.UpdateInstructorProfileRequestDto;
import com.gym.responseDto.customerResponse.InstructorForCustomerResponseDto;
import com.gym.responseDto.instructorResponse.CreateInstructorResponseDto;
import com.gym.responseDto.instructorResponse.GetInstructorProfileResponseDto;
import com.gym.responseDto.instructorResponse.UpdateInstructorProfileResponseDto;
import com.gym.service.GymUserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InstructorMapperTest {
    @Mock
    private GymUserService gymUserService;
    @InjectMocks
    private InstructorMapper instructorMapper;

    @Test
    public void shouldMapCreateInstructorRequestDtoToUserEntity() {
        //Given
        CreateInstructorRequestDto requestDto = new CreateInstructorRequestDto();
        requestDto.setFirstName(RandomStringUtils.randomAlphabetic(7));
        requestDto.setLastName(RandomStringUtils.randomAlphabetic(7));

        when(gymUserService.generateUniqueUserName(requestDto.getFirstName(), requestDto.getLastName()))
                .thenReturn(RandomStringUtils.randomAlphabetic(7));
        //When
        GymUserEntity result = instructorMapper.mapCreateInstructorRequestDtoToUserEntity(requestDto);
        //Then
        assertThat(result.getFirstName()).isEqualTo(requestDto.getFirstName());
        assertThat(result.getLastName()).isEqualTo(requestDto.getLastName());
        assertThat(result.getIsActive()).isTrue();
    }

    @Test
    public void shouldMapUserEntityToInstructorEntity() {
        //Given
        TrainingTypeEntity trainingTypeEntity = new TrainingTypeEntity();
        GymUserEntity gymUserEntity = new GymUserEntity();
        //When
        InstructorEntity result = instructorMapper.mapUserEntityToInstructorEntity(trainingTypeEntity, gymUserEntity);
        //Then
        assertThat(result.getTrainingTypeEntity()).isEqualTo(trainingTypeEntity);
        assertThat(result.getGymUserEntity()).isEqualTo(gymUserEntity);
    }

    @Test
    public void shouldMapToResponseDto() {
        //Given
        GymUserEntity savedUser = new GymUserEntity();
        savedUser.setUserName(RandomStringUtils.randomAlphabetic(7));
        savedUser.setPassword(RandomStringUtils.randomAlphanumeric(7));
        //When
        CreateInstructorResponseDto result = instructorMapper.mapToResponseDto(savedUser);
        //Then
        assertThat(result.getUserName()).isEqualTo(savedUser.getUserName());
        assertThat(result.getPassword()).isEqualTo(savedUser.getPassword());
    }

    @Test
    public void shouldMapInstructorEntityToGetInstructorResponseDto() {
        //Given
        InstructorEntity instructorEntity = new InstructorEntity();
        instructorEntity.setGymUserEntity(new GymUserEntity());
        instructorEntity.getGymUserEntity().setFirstName(RandomStringUtils.randomAlphabetic(7));
        instructorEntity.getGymUserEntity().setLastName(RandomStringUtils.randomAlphabetic(7));
        instructorEntity.setTrainingTypeEntity(new TrainingTypeEntity());
        instructorEntity.getTrainingTypeEntity().setTrainingTypeName(TrainingType.CARDIO);
        instructorEntity.getGymUserEntity().setIsActive(true);
        instructorEntity.setCustomers(new HashSet<>());
        //When
        GetInstructorProfileResponseDto result = instructorMapper.mapInstructorEntityToGetInstructorResponseDto(
                instructorEntity);
        //Then
        assertThat(result.getFirstName()).isEqualTo(instructorEntity.getGymUserEntity().getFirstName());
        assertThat(result.getLastName()).isEqualTo(instructorEntity.getGymUserEntity().getLastName());
        assertThat(result.getIsActive()).isEqualTo(instructorEntity.getGymUserEntity().getIsActive());
    }

    @Test
    public void shouldUpdateInstructorRequestDtoToUserEntity() {
        //Given
        UpdateInstructorProfileRequestDto newData = new UpdateInstructorProfileRequestDto();
        newData.setUserName(RandomStringUtils.randomAlphabetic(7));
        newData.setFirstName(RandomStringUtils.randomAlphabetic(7));
        newData.setLastName(RandomStringUtils.randomAlphabetic(7));
        newData.setIsActive(true);
        //When
        GymUserEntity result = instructorMapper.mapUpdateInstructorRequestDtoToUserEntity(newData);
        //Then
        assertThat(result.getUserName()).isEqualTo(newData.getUserName());
        assertThat(result.getFirstName()).isEqualTo(newData.getFirstName());
        assertThat(result.getLastName()).isEqualTo(newData.getLastName());
        assertThat(result.getIsActive()).isEqualTo(newData.getIsActive());
    }

    @Test
    public void shouldMapInstructorEntityToUpdateInstructorResponseDto() {
        //Given
        InstructorEntity updatedInstructor = new InstructorEntity();
        updatedInstructor.setGymUserEntity(new GymUserEntity());
        updatedInstructor.getGymUserEntity().setUserName(RandomStringUtils.randomAlphabetic(7));
        updatedInstructor.getGymUserEntity().setFirstName(RandomStringUtils.randomAlphabetic(7));
        updatedInstructor.getGymUserEntity().setLastName(RandomStringUtils.randomAlphabetic(7));
        updatedInstructor.getGymUserEntity().setIsActive(true);
        updatedInstructor.setTrainingTypeEntity(new TrainingTypeEntity());
        updatedInstructor.getTrainingTypeEntity().setTrainingTypeName(TrainingType.CARDIO);
        updatedInstructor.setCustomers(new HashSet<>());
        //When
        UpdateInstructorProfileResponseDto result = instructorMapper.mapInstructorEntityToUpdateInstructorResponseDto(
                updatedInstructor);
        //Then
        assertThat(result. getUserName()).isEqualTo(updatedInstructor.getGymUserEntity().getUserName());
        assertThat(result.getFirstName()).isEqualTo(updatedInstructor.getGymUserEntity().getFirstName());
        assertThat(result.getLastName()).isEqualTo(updatedInstructor.getGymUserEntity().getLastName());
        assertThat(result.getIsActive()).isEqualTo(updatedInstructor.getGymUserEntity().getIsActive());
    }

    @Test
    public void shouldMapInstructorEntitiesToInstructorDtos() {
        //Given
        Set<InstructorEntity> instructorEntities = new HashSet<>();
        InstructorEntity instructorEntity = new InstructorEntity();
        instructorEntity.setGymUserEntity(new GymUserEntity());
        instructorEntity.getGymUserEntity().setUserName(RandomStringUtils.randomAlphabetic(7));
        instructorEntity.getGymUserEntity().setFirstName(RandomStringUtils.randomAlphabetic(7));
        instructorEntity.getGymUserEntity().setLastName(RandomStringUtils.randomAlphabetic(7));
        instructorEntity.setTrainingTypeEntity(new TrainingTypeEntity());
        instructorEntity.getTrainingTypeEntity().setTrainingTypeName(TrainingType.CARDIO);
        instructorEntities.add(instructorEntity);
        //When
        List<InstructorForCustomerResponseDto> result = instructorMapper.mapInstructorEntitiesToInstructorResponseDto(
                instructorEntities);
        //Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUserName()).isEqualTo(instructorEntity.getGymUserEntity().getUserName());
        assertThat(result.get(0).getFirstName()).isEqualTo(instructorEntity.getGymUserEntity().getFirstName());
        assertThat(result.get(0).getLastName()).isEqualTo(instructorEntity.getGymUserEntity().getLastName());
    }
}