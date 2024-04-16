package mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.gym.dto.request.instructor.CreateInstructorRequestDto;
import com.gym.dto.request.instructor.UpdateInstructorProfileRequestDto;
import com.gym.dto.response.customer.InstructorForCustomerResponseDto;
import com.gym.dto.response.instructor.CreateInstructorResponseDto;
import com.gym.dto.response.instructor.GetInstructorProfileResponseDto;
import com.gym.dto.response.instructor.UpdateInstructorProfileResponseDto;
import com.gym.entity.GymUserEntity;
import com.gym.entity.InstructorEntity;
import com.gym.entity.TrainingType;
import com.gym.entity.TrainingTypeEntity;
import com.gym.mapper.InstructorMapper;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class InstructorMapperTest {
    @InjectMocks
    private InstructorMapper instructorMapper;

    @Test
    public void shouldMapCreateInstructorRequestDtoToUserEntity() {
        //Given
        CreateInstructorRequestDto requestDto = new CreateInstructorRequestDto();
        requestDto.setFirstName(RandomStringUtils.randomAlphabetic(7));
        requestDto.setLastName(RandomStringUtils.randomAlphabetic(7));

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
        CreateInstructorResponseDto result = instructorMapper.mapToResponseDto(savedUser, savedUser.getPassword());
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
        GetInstructorProfileResponseDto result =
            instructorMapper.mapInstructorEntityToGetInstructorResponseDto(instructorEntity);
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
        UpdateInstructorProfileResponseDto result =
            instructorMapper.mapInstructorEntityToUpdateInstructorResponseDto(updatedInstructor);
        //Then
        assertThat(result.getUserName()).isEqualTo(updatedInstructor.getGymUserEntity().getUserName());
        assertThat(result.getFirstName()).isEqualTo(updatedInstructor.getGymUserEntity().getFirstName());
        assertThat(result.getLastName()).isEqualTo(updatedInstructor.getGymUserEntity().getLastName());
        assertThat(result.getIsActive()).isEqualTo(updatedInstructor.getGymUserEntity().getIsActive());
    }

    @Test
    public void shouldMapInstructorEntitiesToInstructorDtos() {
        //Given
        final Set<InstructorEntity> instructorEntities = new HashSet<>();
        InstructorEntity instructorEntity = new InstructorEntity();
        instructorEntity.setGymUserEntity(new GymUserEntity());
        instructorEntity.getGymUserEntity().setUserName(RandomStringUtils.randomAlphabetic(7));
        instructorEntity.getGymUserEntity().setFirstName(RandomStringUtils.randomAlphabetic(7));
        instructorEntity.getGymUserEntity().setLastName(RandomStringUtils.randomAlphabetic(7));
        instructorEntity.setTrainingTypeEntity(new TrainingTypeEntity());
        instructorEntity.getTrainingTypeEntity().setTrainingTypeName(TrainingType.CARDIO);
        instructorEntities.add(instructorEntity);
        //When
        List<InstructorForCustomerResponseDto> result =
            instructorMapper.mapInstructorEntitiesToInstructorResponseDto(instructorEntities);
        //Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUserName()).isEqualTo(instructorEntity.getGymUserEntity().getUserName());
        assertThat(result.get(0).getFirstName()).isEqualTo(instructorEntity.getGymUserEntity().getFirstName());
        assertThat(result.get(0).getLastName()).isEqualTo(instructorEntity.getGymUserEntity().getLastName());
    }
}
