package mapperTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.gym.entity.CustomerEntity;
import com.gym.entity.GymUserEntity;
import com.gym.entity.InstructorEntity;
import com.gym.entity.TrainingTypeEntity;
import com.gym.mapper.CustomerMapper;
import com.gym.requestDto.customerRequest.CreateCustomerRequestDto;
import com.gym.responseDto.customerResponse.CreateCustomerResponseDto;
import com.gym.responseDto.customerResponse.InstructorForCustomerResponseDto;
import com.gym.responseDto.customerResponse.UpdateCustomerProfileResponseDto;
import com.gym.service.GymUserService;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomerMapperTest {
    @Mock
    private GymUserService gymUserService;

    @InjectMocks
    private CustomerMapper customerMapper;

    @Test
    public void shouldMapCreateCustomerRequestDtoToUserEntity() {
        // Given
        String username = RandomStringUtils.randomAlphabetic(7);
        CreateCustomerRequestDto customerDto = new CreateCustomerRequestDto();
        customerDto.setFirstName(RandomStringUtils.randomAlphabetic(7));
        customerDto.setLastName(RandomStringUtils.randomAlphabetic(7));
        when(gymUserService.generateUniqueUserName(anyString(), anyString())).thenReturn(username);

        // When
        GymUserEntity result = customerMapper.mapCreateCustomerRequestDtoToUserEntity(customerDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo(customerDto.getFirstName());
        assertThat(result.getLastName()).isEqualTo(customerDto.getLastName());
        assertThat(result.getUserName()).isEqualTo(username);
    }

    @Test
    public void shouldMapCreateCustomerRequestDtoToCustomerEntity() {
        // Given
        CreateCustomerRequestDto customerDto = new CreateCustomerRequestDto();
        customerDto.setAddress(RandomStringUtils.randomAlphabetic(7));
        customerDto.setDateOfBirth("1990-01-01");

        // When
        CustomerEntity result = customerMapper.mapCreateCustomerRequestDtoToCustomerEntity(customerDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getAddress()).isEqualTo(customerDto.getAddress());
        assertThat(result.getDateOfBirth()).isEqualTo(Date.valueOf(customerDto.getDateOfBirth()));
    }

    @Test
    public void shouldMapCustomerEntityToCreateResponseDto() {
        // Given
        GymUserEntity userEntity = new GymUserEntity();
        userEntity.setUserName(RandomStringUtils.randomAlphabetic(7));
        userEntity.setPassword(RandomStringUtils.randomAlphabetic(7));

        // When
        CreateCustomerResponseDto result = customerMapper.mapCustomerEntityToCreateResponseDto(userEntity);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUserName()).isEqualTo(userEntity.getUserName());
        assertThat(result.getPassword()).isEqualTo(userEntity.getPassword());
    }

    @Test
    public void shouldMapCustomerEntityToUpdateCustomerResponseDto() {
        // Given
        String username = RandomStringUtils.randomAlphabetic(7);
        String firstName = RandomStringUtils.randomAlphabetic(7);
        String lastName = RandomStringUtils.randomAlphabetic(7);
        String address = RandomStringUtils.randomAlphabetic(7);
        CustomerEntity customerEntity = new CustomerEntity();
        GymUserEntity userEntity = new GymUserEntity();
        userEntity.setUserName(username);
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userEntity.setIsActive(true);
        customerEntity.setGymUserEntity(userEntity);
        customerEntity.setAddress(address);
        customerEntity.setDateOfBirth(Date.valueOf("1990-01-01"));


        Set<InstructorEntity> instructors = new HashSet<>();
        InstructorEntity instructorEntity = new InstructorEntity();
        TrainingTypeEntity trainingTypeEntity = new TrainingTypeEntity();
        instructorEntity.setGymUserEntity(userEntity);
        instructorEntity.setTrainingTypeEntity(trainingTypeEntity);
        instructors.add(instructorEntity);
        customerEntity.setInstructors(instructors);

        // When
        UpdateCustomerProfileResponseDto result = customerMapper.mapCustomerEntityToUpdateCustomerResponseDto(
            customerEntity);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUserName()).isEqualTo(userEntity.getUserName());
        assertThat(result.getFirstName()).isEqualTo(userEntity.getFirstName());
        assertThat(result.getLastName()).isEqualTo(userEntity.getLastName());
        assertThat(result.getAddress()).isEqualTo(customerEntity.getAddress());
        assertThat(result.getDateOfBirth()).isEqualTo(customerEntity.getDateOfBirth().toString());
        assertThat(result.getIsActive()).isEqualTo(customerEntity.getGymUserEntity().getIsActive());

        List<InstructorForCustomerResponseDto> instructorDtos = result.getInstructors();
        assertThat(instructorDtos).hasSize(1);
        InstructorForCustomerResponseDto instructorDto = instructorDtos.get(0);
        assertThat(instructorDto.getUserName()).isEqualTo(userEntity.getUserName());
        assertThat(instructorDto.getFirstName()).isEqualTo(userEntity.getFirstName());
        assertThat(instructorDto.getLastName()).isEqualTo(userEntity.getLastName());
    }
}
