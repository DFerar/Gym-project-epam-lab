package mapperTest;

import static org.assertj.core.api.Assertions.assertThat;

import com.gym.entity.CustomerEntity;
import com.gym.entity.GymUserEntity;
import com.gym.entity.InstructorEntity;
import com.gym.entity.TrainingEntity;
import com.gym.entity.TrainingType;
import com.gym.entity.TrainingTypeEntity;
import com.gym.mapper.TrainingMapper;
import com.gym.requestDto.trainingRequest.CreateTrainingRequestDto;
import com.gym.responseDto.trainingResponse.CustomerTrainingsResponseDto;
import com.gym.responseDto.trainingResponse.InstructorTrainingsResponseDto;
import com.gym.responseDto.trainingResponse.TrainingTypeResponseDto;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TrainingMapperTest {
    @InjectMocks
    private TrainingMapper trainingMapper;

    @Test
    public void shouldMapCustomerTrainingEntitiesToTrainingDtos() {
        //Given
        List<TrainingEntity> trainingEntities = new ArrayList<>();
        TrainingEntity trainingEntity = createMockTrainingEntity();
        trainingEntities.add(trainingEntity);
        //When
        List<CustomerTrainingsResponseDto> result =
            trainingMapper.mapCustomerTrainingEntitiesToTrainingDtos(trainingEntities);
        //Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTrainingName()).isEqualTo(trainingEntity.getTrainingName());
        assertThat(result.get(0).getTrainingDate()).isEqualTo(trainingEntity.getTrainingDate().toString());
        assertThat(result.get(0).getTrainingDuration()).isEqualTo(trainingEntity.getTrainingDuration());
    }

    @Test
    public void shouldMapInstructorTrainingEntitiesToTrainingDtos() {
        //Given
        List<TrainingEntity> trainingEntities = new ArrayList<>();
        TrainingEntity trainingEntity = createMockTrainingEntity();
        trainingEntities.add(trainingEntity);
        //When
        List<InstructorTrainingsResponseDto> result = trainingMapper
            .mapInstructorTrainingEntitiesToTrainingDtos(trainingEntities);
        //Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTrainingName()).isEqualTo(trainingEntity.getTrainingName());
        assertThat(result.get(0).getTrainingDate()).isEqualTo(trainingEntity.getTrainingDate().toString());
        assertThat(result.get(0).getTrainingDuration()).isEqualTo(trainingEntity.getTrainingDuration());
    }

    @Test
    public void shouldMapCreateTrainingRequestDtoToTrainingEntity() {
        //Given
        CreateTrainingRequestDto requestDto = new CreateTrainingRequestDto();
        requestDto.setCustomerUserName(RandomStringUtils.randomAlphabetic(7));
        requestDto.setInstructorUserName(RandomStringUtils.randomAlphabetic(7));
        requestDto.setTrainingDate("2024-03-25");
        requestDto.setTrainingDuration(60);
        requestDto.setTrainingName(RandomStringUtils.randomAlphabetic(10));
        //When
        TrainingEntity result = trainingMapper.mapCreateTrainingRequestDtoToTrainingEntity(requestDto);
        //Then
        assertThat(result.getCustomer().getGymUserEntity().getUserName()).isEqualTo(requestDto.getCustomerUserName());
        assertThat(result.getInstructor().getGymUserEntity().getUserName()).isEqualTo(
            requestDto.getInstructorUserName());
        assertThat(result.getTrainingDuration()).isEqualTo(requestDto.getTrainingDuration());
        assertThat(result.getTrainingName()).isEqualTo(requestDto.getTrainingName());
    }

    @Test
    public void shouldMapTrainingTypeEntitiesToTrainingTypeResponseDto() {
        //Given
        List<TrainingTypeEntity> trainingTypeEntities = new ArrayList<>();
        TrainingTypeEntity trainingTypeEntity = new TrainingTypeEntity();
        trainingTypeEntity.setId(1L);
        trainingTypeEntity.setTrainingTypeName(TrainingType.CARDIO);
        trainingTypeEntities.add(trainingTypeEntity);
        //When
        List<TrainingTypeResponseDto> result = trainingMapper.mapTrainingTypeEntitiesToTrainingTypeResponseDto(
            trainingTypeEntities);
        //Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTrainingTypeName()).isEqualTo(trainingTypeEntity.getTrainingTypeName());
    }

    private TrainingEntity createMockTrainingEntity() {
        TrainingEntity trainingEntity = new TrainingEntity();
        trainingEntity.setTrainingName(RandomStringUtils.randomAlphabetic(10));
        trainingEntity.setTrainingDate(Date.valueOf("2024-03-25"));
        trainingEntity.setTrainingDuration(60);
        TrainingTypeEntity trainingTypeEntity = new TrainingTypeEntity();
        trainingTypeEntity.setTrainingTypeName(TrainingType.CARDIO);
        trainingEntity.setTrainingType(trainingTypeEntity);
        InstructorEntity instructorEntity = new InstructorEntity();
        GymUserEntity instructorUserEntity = new GymUserEntity();
        instructorUserEntity.setUserName(RandomStringUtils.randomAlphabetic(7));
        instructorEntity.setGymUserEntity(instructorUserEntity);
        trainingEntity.setInstructor(instructorEntity);
        CustomerEntity customerEntity = new CustomerEntity();
        GymUserEntity customerUserEntity = new GymUserEntity();
        customerUserEntity.setUserName(RandomStringUtils.randomAlphabetic(7));
        customerEntity.setGymUserEntity(customerUserEntity);
        trainingEntity.setCustomer(customerEntity);
        return trainingEntity;
    }
}
