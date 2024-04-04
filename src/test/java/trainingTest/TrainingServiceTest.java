package trainingTest;

import static com.gym.entity.TrainingType.CARDIO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gym.entity.CustomerEntity;
import com.gym.entity.GymUserEntity;
import com.gym.entity.InstructorEntity;
import com.gym.entity.TrainingEntity;
import com.gym.entity.TrainingType;
import com.gym.entity.TrainingTypeEntity;
import com.gym.repository.CustomerRepository;
import com.gym.repository.InstructorRepository;
import com.gym.repository.TrainingRepository;
import com.gym.repository.TrainingTypeRepository;
import com.gym.service.TrainingService;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


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
    @InjectMocks
    private TrainingService trainingService;

    @Test
    public void shouldCreateTraining() {
        //Given
        String userNameForCustomer = RandomStringUtils.randomAlphabetic(7);
        String userNameForInstructor = RandomStringUtils.randomAlphabetic(7);

        TrainingEntity trainingEntity = new TrainingEntity();
        CustomerEntity customerEntity = new CustomerEntity();
        InstructorEntity instructorEntity = new InstructorEntity();
        TrainingTypeEntity trainingTypeEntity = new TrainingTypeEntity();
        GymUserEntity gymUserEntityForCustomer = new GymUserEntity();
        gymUserEntityForCustomer.setUserName(userNameForCustomer);
        GymUserEntity gymUserEntityForInstructor = new GymUserEntity();
        gymUserEntityForInstructor.setUserName(userNameForInstructor);
        customerEntity.setGymUserEntity(gymUserEntityForCustomer);
        instructorEntity.setGymUserEntity(gymUserEntityForInstructor);
        instructorEntity.setTrainingTypeEntity(trainingTypeEntity);


        trainingEntity.setCustomer(customerEntity);
        trainingEntity.setInstructor(instructorEntity);

        when(customerRepository.findCustomerEntityByGymUserEntityUserName(anyString()))
            .thenReturn(customerEntity);
        when(instructorRepository.findInstructorEntityByGymUserEntityUserName(anyString()))
            .thenReturn(instructorEntity);
        when(trainingTypeRepository.findByTrainingTypeName(any()))
            .thenReturn(trainingTypeEntity);
        when(trainingRepository.save(trainingEntity))
            .thenReturn(trainingEntity);
        //When
        trainingService.createTraining(trainingEntity);
        //Assert
        verify(customerRepository, times(1)).findCustomerEntityByGymUserEntityUserName(
            anyString());
        verify(instructorRepository, times(1)).findInstructorEntityByGymUserEntityUserName(
            anyString());
        verify(trainingTypeRepository, times(1)).findByTrainingTypeName(any());
        verify(trainingRepository, times(1)).save(trainingEntity);
    }

    @Test
    public void shouldGetCustomerListOfTrainings() {
        //Given
        String customerUserName = RandomStringUtils.randomAlphabetic(7);
        LocalDate fromDate = LocalDate.of(2024, 1, 1);
        LocalDate toDate = LocalDate.of(2024, 3, 3);
        String instructorName = RandomStringUtils.randomAlphabetic(7);
        TrainingType trainingTypeName = CARDIO;

        List<TrainingEntity> expectedTrainings = Collections.singletonList(new TrainingEntity());
        when(trainingRepository.findTrainingsByCustomerAndCriteria(customerUserName, fromDate, toDate,
            instructorName, trainingTypeName))
            .thenReturn(expectedTrainings);
        //When
        List<TrainingEntity> actualTrainings = trainingService.getCustomerListOfTrainings(customerUserName, fromDate,
            toDate, instructorName, trainingTypeName);
        //Assert
        assertThat(actualTrainings).isEqualTo(expectedTrainings);
    }

    @Test
    public void shouldGetInstructorListOfTrainings() {
        String instructorUserName = RandomStringUtils.randomAlphabetic(7);
        LocalDate fromDate = LocalDate.of(2024, 1, 1);
        LocalDate toDate = LocalDate.of(2024, 3, 3);
        String customerName = RandomStringUtils.randomAlphabetic(7);

        List<TrainingEntity> expectedTrainings = Collections.singletonList(new TrainingEntity());
        when(trainingRepository.findTrainingsByInstructorAndCriteria(instructorUserName, fromDate, toDate,
            customerName))
            .thenReturn(expectedTrainings);

        List<TrainingEntity> actualTrainings = trainingService.getInstructorListOfTrainings(instructorUserName,
            fromDate, toDate, customerName);

        assertThat(actualTrainings).isEqualTo(expectedTrainings);
    }
}
