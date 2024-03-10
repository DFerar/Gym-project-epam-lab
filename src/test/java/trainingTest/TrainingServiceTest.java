package trainingTest;

import com.gym.dto.TrainingDto;
import com.gym.entity.CustomerEntity;
import com.gym.entity.InstructorEntity;
import com.gym.entity.TrainingEntity;
import com.gym.entity.TrainingTypeEntity;
import com.gym.repository.CustomerRepository;
import com.gym.repository.InstructorRepository;
import com.gym.repository.TrainingRepository;
import com.gym.repository.TrainingTypeRepository;
import com.gym.service.AuthenticationService;
import com.gym.service.TrainingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TrainingServiceTest {
    @Mock
    private TrainingRepository trainingRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private InstructorRepository instructorRepository;
    @Mock
    private TrainingTypeRepository trainingTypeRepository;
    @Mock
    private AuthenticationService authenticationService;
    @InjectMocks
    private TrainingService trainingService;

    @Test
    public void createTrainingTest() {
        String loginUserName = "user";
        String loginPassword = "password";
        TrainingDto trainingDto = new TrainingDto(1, 1, 1, "Test.training", 1,
                Date.valueOf("2024-01-01"), 1);

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(1);

        InstructorEntity instructorEntity = new InstructorEntity();
        instructorEntity.setId(1);

        TrainingTypeEntity trainingTypeEntity = new TrainingTypeEntity();
        trainingTypeEntity.setId(1);

        TrainingEntity savedTraining = new TrainingEntity();
        savedTraining.setId(1);
        savedTraining.setCustomer(customerEntity);
        savedTraining.setInstructor(instructorEntity);
        savedTraining.setTrainingType(trainingTypeEntity);
        savedTraining.setTrainingName("Test.training");
        savedTraining.setTrainingDate(Date.valueOf("2024-01-01"));
        savedTraining.setTrainingDuration(1);

        when(authenticationService.matchInstructorCredentials(loginUserName, loginPassword)).thenReturn(true);
        when(customerRepository.findById(trainingDto.getCustomerId())).thenReturn(Optional.of(customerEntity));
        when(instructorRepository.findById(trainingDto.getInstructorId())).thenReturn(Optional.of(instructorEntity));
        when(trainingTypeRepository.findById(trainingDto.getTrainingTypeId())).thenReturn(Optional.of(trainingTypeEntity));
        when(trainingRepository.save(any(TrainingEntity.class))).thenReturn(savedTraining);

        TrainingDto result = trainingService.createTraining(loginUserName, loginPassword, trainingDto);

        assertThat(result).isEqualTo(trainingDto);
    }

    @Test
    public void getCustomerListOfTrainingTest() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(1);

        InstructorEntity instructorEntity = new InstructorEntity();
        instructorEntity.setId(1);

        TrainingTypeEntity trainingTypeEntity = new TrainingTypeEntity();
        trainingTypeEntity.setId(1);

        TrainingEntity trainingEntity = new TrainingEntity();
        trainingEntity.setId(1);
        trainingEntity.setCustomer(customerEntity);
        trainingEntity.setInstructor(instructorEntity);
        trainingEntity.setTrainingType(trainingTypeEntity);
        trainingEntity.setTrainingName("Test.training");
        trainingEntity.setTrainingDate(Date.valueOf("2024-01-01"));
        trainingEntity.setTrainingDuration(1);

        Date fromDate = Date.valueOf("2022-01-01");
        Date toDate = Date.valueOf("2024-12-31");
        String instructorName = "John Doe";
        String trainingTypeName = "Yoga";

        List<TrainingEntity> trainingEntities = List.of(trainingEntity);

        when(trainingRepository.findTrainingsByCustomerAndCriteria(
                customerEntity.getId(), fromDate, toDate, instructorName, trainingTypeName)).thenReturn(trainingEntities);

        List<TrainingDto> result = trainingService.getCustomerListOfTrainings(customerEntity, fromDate, toDate,
                instructorName, trainingTypeName);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void getInstructorListOfTrainings() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(1);

        InstructorEntity instructorEntity = new InstructorEntity();
        instructorEntity.setId(1);

        TrainingTypeEntity trainingTypeEntity = new TrainingTypeEntity();
        trainingTypeEntity.setId(1);

        TrainingEntity trainingEntity = new TrainingEntity();
        trainingEntity.setId(1);
        trainingEntity.setCustomer(customerEntity);
        trainingEntity.setInstructor(instructorEntity);
        trainingEntity.setTrainingType(trainingTypeEntity);
        trainingEntity.setTrainingName("Test.training");
        trainingEntity.setTrainingDate(Date.valueOf("2024-01-01"));
        trainingEntity.setTrainingDuration(1);

        Date fromDate = Date.valueOf("2022-01-01");
        Date toDate = Date.valueOf("2024-12-31");
        String customerName = "Test.Customer";

        List<TrainingEntity> trainingEntities = List.of(trainingEntity);

        when(trainingRepository.findTrainingsByInstructorAndCriteria(instructorEntity.getId(), fromDate, toDate, customerName))
                .thenReturn(trainingEntities);

        List<TrainingDto> result = trainingService.getInstructorListOfTrainings(instructorEntity, fromDate, toDate,
                customerName);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
    }
}
