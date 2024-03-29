package instructorTest;

import static com.gym.entity.TrainingType.CARDIO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gym.entity.GymUserEntity;
import com.gym.entity.InstructorEntity;
import com.gym.entity.TrainingType;
import com.gym.entity.TrainingTypeEntity;
import com.gym.mapper.InstructorMapper;
import com.gym.repository.GymUserRepository;
import com.gym.repository.InstructorRepository;
import com.gym.repository.TrainingTypeRepository;
import com.gym.service.GymUserService;
import com.gym.service.InstructorService;
import com.gym.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class InstructorServiceTest {
    @Mock
    private GymUserService gymUserService;

    @Mock
    private InstructorRepository instructorRepository;
    @Mock
    private InstructorMapper instructorMapper;

    @Mock
    private GymUserRepository gymUserRepository;

    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @InjectMocks
    private InstructorService instructorService;

    @Test
    public void shouldCreateInstructor() {
        //Given
        String password = Utils.generatePassword();
        String firstName = RandomStringUtils.randomAlphabetic(7);
        String lastName = RandomStringUtils.randomAlphabetic(7);
        String userName = RandomStringUtils.randomAlphabetic(7);
        GymUserEntity gymUserEntity = new GymUserEntity(1L, firstName, lastName,
            userName, password, true);

        TrainingTypeEntity trainingTypeEntity = new TrainingTypeEntity(1L, CARDIO);

        InstructorEntity instructorEntity = new InstructorEntity(1L, trainingTypeEntity, gymUserEntity,
            null);

        when(gymUserRepository.save(any(GymUserEntity.class))).thenReturn(gymUserEntity);
        when(instructorMapper.mapUserEntityToInstructorEntity(any(), any())).thenReturn(instructorEntity);
        when(trainingTypeRepository.findByTrainingTypeName(any(TrainingType.class))).thenReturn(trainingTypeEntity);
        //When
        InstructorEntity result = instructorService.createInstructor(gymUserEntity,
            trainingTypeEntity.getTrainingTypeName());
        //Assert
        assertThat(result).isEqualTo(instructorEntity);
    }

    @Test
    public void shouldGetInstructorByUserName() {
        //Given
        String password = Utils.generatePassword();
        String userName = RandomStringUtils.randomAlphabetic(7);
        String firstName = RandomStringUtils.randomAlphabetic(7);
        String lastName = RandomStringUtils.randomAlphabetic(7);
        String userNameOfInstructor = RandomStringUtils.randomAlphabetic(7);


        GymUserEntity gymUserEntity = new GymUserEntity(1L, firstName, lastName,
            userNameOfInstructor, password, true);

        TrainingTypeEntity trainingTypeEntity = new TrainingTypeEntity(1L, CARDIO);

        InstructorEntity instructorEntity = new InstructorEntity(1L, trainingTypeEntity, gymUserEntity,
            null);

        when(instructorRepository.findInstructorEntityByGymUserEntityUserName(userName)).thenReturn(instructorEntity);
        //When
        InstructorEntity result = instructorService.getInstructorByUsername(userName);
        //Assert
        assertThat(result).isEqualTo(instructorEntity);
    }

    @Test
    public void shouldUpdateInstructor() {
        //Given
        String password = Utils.generatePassword();
        String firstName = RandomStringUtils.randomAlphabetic(7);
        String lastName = RandomStringUtils.randomAlphabetic(7);
        String userName = RandomStringUtils.randomAlphabetic(7);

        GymUserEntity updatedUser = new GymUserEntity(1L, firstName, lastName,
            userName, password, true);

        TrainingTypeEntity trainingTypeEntity = new TrainingTypeEntity(1L, CARDIO);

        InstructorEntity instructorEntity = new InstructorEntity(1L, trainingTypeEntity, updatedUser,
            null);

        when(instructorRepository.findInstructorEntityByGymUserEntityUserName(any()))
            .thenReturn(instructorEntity);
        when(gymUserService.updateUser(any())).thenReturn(updatedUser);
        when(instructorRepository.save(any(InstructorEntity.class))).thenReturn(instructorEntity);
        when(trainingTypeRepository.findByTrainingTypeName(any(TrainingType.class))).thenReturn(trainingTypeEntity);
        //When
        InstructorEntity result = instructorService.updateInstructor(updatedUser,
            trainingTypeEntity.getTrainingTypeName());
        //Assert
        assertThat(result).isEqualTo(instructorEntity);
    }

    @Test
    public void shouldChangeInstructorActivity() {
        //Given
        String password = Utils.generatePassword();
        Long userId = 1L;
        Boolean newActivity = false;
        String firstName = RandomStringUtils.randomAlphabetic(7);
        String lastName = RandomStringUtils.randomAlphabetic(7);
        String userName = RandomStringUtils.randomAlphabetic(7);

        GymUserEntity gymUserEntity = new GymUserEntity(userId, firstName, lastName,
            userName, password, true);

        when(gymUserRepository.findByUserName(userName)).thenReturn(gymUserEntity);
        //When
        instructorService.changeInstructorActivity(userName, newActivity);
        //Assert
        verify(gymUserRepository, times(1)).save(gymUserEntity);
        assertThat(gymUserEntity.getIsActive()).isEqualTo(newActivity);
    }

    @Test
    public void shouldGetInstructorsNotAssignedToCustomerByCustomerUserName() {
        //Given
        String customerUsername = RandomStringUtils.randomAlphabetic(7);

        when(instructorRepository.findUnassignedInstructorsByCustomerUsername(customerUsername)).thenReturn(
            new ArrayList<>());
        //When
        List<InstructorEntity> result = instructorService.getInstructorsNotAssignedToCustomerByCustomerUserName(
            customerUsername);
        //Assert
        assertThat(result).isNotNull();
    }
}
