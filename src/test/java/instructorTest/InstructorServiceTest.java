package instructorTest;

import com.gym.dto.InstructorDto;
import com.gym.dto.TrainingDto;
import com.gym.entity.GymUserEntity;
import com.gym.entity.InstructorEntity;
import com.gym.entity.TrainingTypeEntity;
import com.gym.repository.GymUserRepository;
import com.gym.repository.InstructorRepository;
import com.gym.repository.TrainingTypeRepository;
import com.gym.service.AuthenticationService;
import com.gym.service.GymUserService;
import com.gym.service.InstructorService;
import com.gym.service.TrainingService;
import com.gym.utils.Utils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class InstructorServiceTest {
    @Mock
    private GymUserService gymUserService;

    @Mock
    private InstructorRepository instructorRepository;

    @Mock
    private GymUserRepository gymUserRepository;

    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @Mock
    private TrainingService trainingService;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private InstructorService instructorService;

    @Test
    public void shouldCreateInstructor() {
        //Given
        String password = Utils.generatePassword();
        InstructorDto instructorDto = new InstructorDto(1, "CARDIO", 1, "Test",
                "Instructor", "Test.Instructor", true);

        GymUserEntity gymUserEntity = new GymUserEntity(1, "Test", "Instructor",
                "Test.Instructor", password, true);

        TrainingTypeEntity trainingTypeEntity = new TrainingTypeEntity(1, "CARDIO");

        InstructorEntity instructorEntity = new InstructorEntity(1, trainingTypeEntity, gymUserEntity, null);

        when(gymUserRepository.save(any(GymUserEntity.class))).thenReturn(gymUserEntity);
        when(instructorRepository.save(any(InstructorEntity.class))).thenReturn(instructorEntity);
        when(gymUserService.generateUniqueUserName(any(String.class), any(String.class))).thenReturn("Test.Instructor");
        when(trainingTypeRepository.findByTrainingTypeName(any(String.class))).thenReturn(trainingTypeEntity);
        //When
        InstructorDto result = instructorService.createInstructor(instructorDto);
        //Assert
        assertThat(result).isEqualTo(instructorDto);
    }

    @Test
    public void shouldGetInstructorById() {
        //Given
        String loginUserName = RandomStringUtils.randomAlphabetic(7);
        String loginPassword = Utils.generatePassword();
        String password = Utils.generatePassword();
        Integer instructorId = 1;

        InstructorDto instructorDto = new InstructorDto(instructorId, "CARDIO", 1, "Test",
                "Instructor", "Test.Instructor", true);

        GymUserEntity gymUserEntity = new GymUserEntity(1, "Test", "Instructor",
                "Test.Instructor", password, true);

        TrainingTypeEntity trainingTypeEntity = new TrainingTypeEntity(1, "CARDIO");

        InstructorEntity instructorEntity = new InstructorEntity(1, trainingTypeEntity, gymUserEntity, null);

        when(authenticationService.matchInstructorCredentials(loginUserName, loginPassword)).thenReturn(true);
        when(instructorRepository.findById(instructorId)).thenReturn(Optional.of(instructorEntity));
        //When
        InstructorDto result = instructorService.getInstructorById(loginUserName, loginPassword, instructorId);
        //Assert
        assertThat(result).isEqualTo(instructorDto);
    }

    @Test
    public void shouldGetInstructorByUserName() {
        //Given
        String loginUserName = RandomStringUtils.randomAlphabetic(7);
        String loginPassword = Utils.generatePassword();
        String password = Utils.generatePassword();
        String userName = RandomStringUtils.randomAlphabetic(7);

        InstructorDto instructorDto = new InstructorDto(1, "CARDIO", 1, "Test",
                "Instructor", "Test.Instructor", true);

        GymUserEntity gymUserEntity = new GymUserEntity(1, "Test", "Instructor",
                "Test.Instructor", password, true);

        TrainingTypeEntity trainingTypeEntity = new TrainingTypeEntity(1, "CARDIO");

        InstructorEntity instructorEntity = new InstructorEntity(1, trainingTypeEntity, gymUserEntity, null);

        when(authenticationService.matchInstructorCredentials(loginUserName, loginPassword)).thenReturn(true);
        when(instructorRepository.findInstructorEntityByGymUserEntity_UserName(userName)).thenReturn(instructorEntity);
        //When
        InstructorDto result = instructorService.getInstructorByUsername(loginUserName, loginPassword, userName);
        //Assert
        assertThat(result).isEqualTo(instructorDto);
    }

    @Test
    public void shouldUpdateInstructor() {
        //Given
        String loginUserName = RandomStringUtils.randomAlphabetic(7);
        String loginPassword = Utils.generatePassword();
        String password = Utils.generatePassword();


        InstructorDto newData = new InstructorDto(1, "CARDIO", 1, "Test",
                "Instructor", "Test.Instructor", true);

        GymUserEntity updatedUser = new GymUserEntity(1, "Test", "Instructor",
                "Test.Instructor", password, true);

        TrainingTypeEntity trainingTypeEntity = new TrainingTypeEntity(1, "CARDIO");

        InstructorEntity instructorEntity = new InstructorEntity(1, trainingTypeEntity, updatedUser, null);

        when(authenticationService.matchInstructorCredentials(loginUserName, loginPassword)).thenReturn(true);
        when(instructorRepository.findById(newData.getId())).thenReturn(Optional.of(new InstructorEntity()));
        when(gymUserService.updateUser(anyInt(), anyString(), anyString(), anyBoolean())).thenReturn(updatedUser);
        when(instructorRepository.save(any(InstructorEntity.class))).thenReturn(instructorEntity);
        when(trainingTypeRepository.findByTrainingTypeName(any(String.class))).thenReturn(trainingTypeEntity);
        //When
        InstructorDto result = instructorService.updateInstructor(loginUserName, loginPassword, newData);
        //Assert
        assertThat(result).isEqualTo(newData);
    }

    @Test
    public void shouldChangeInstructorPassword() {
        //Given
        String loginUserName = RandomStringUtils.randomAlphabetic(7);
        String loginPassword = Utils.generatePassword();
        String password = Utils.generatePassword();
        Integer userId = 1;
        String newPassword = Utils.generatePassword();

        GymUserEntity gymUserEntity = new GymUserEntity(userId, "Test", "Instructor",
                "Test.Instructor", password, true);

        when(gymUserRepository.findById(userId)).thenReturn(Optional.of(gymUserEntity));
        when(authenticationService.matchInstructorCredentials(loginUserName, loginPassword)).thenReturn(true);
        //When
        instructorService.changeInstructorPassword(loginUserName, loginPassword, userId, newPassword);
        //Assert
        verify(gymUserRepository, times(1)).save(gymUserEntity);
        assertThat(gymUserEntity.getPassword()).isEqualTo(newPassword);
    }

    @Test
    public void shouldChangeInstructorActivity() {
        //Given
        String loginUserName = RandomStringUtils.randomAlphabetic(7);
        String loginPassword = Utils.generatePassword();
        String password = Utils.generatePassword();
        Integer userId = 1;
        Boolean newActivity = false;

        GymUserEntity gymUserEntity = new GymUserEntity(userId, "Test", "Instructor",
                "Test.Instructor", password, true);

        when(gymUserRepository.findById(userId)).thenReturn(Optional.of(gymUserEntity));
        when(authenticationService.matchInstructorCredentials(loginUserName, loginPassword)).thenReturn(true);
        //When
        instructorService.changeInstructorActivity(loginUserName, loginPassword, userId, newActivity);
        //Assert
        verify(gymUserRepository, times(1)).save(gymUserEntity);
        assertThat(gymUserEntity.getIsActive()).isEqualTo(newActivity);
    }

    @Test
    public void shouldGetInstructorTrainings() {
        //Given
        String loginUserName = RandomStringUtils.randomAlphabetic(7);
        String loginPassword = Utils.generatePassword();
        String instructorName = RandomStringUtils.randomAlphabetic(7);
        Date fromDate = Date.valueOf("2022-01-01");
        Date toDate = Date.valueOf("2022-12-31");
        String customerName = RandomStringUtils.randomAlphabetic(7);

        InstructorEntity instructorEntity = new InstructorEntity();
        instructorEntity.setId(1);

        when(instructorRepository.findInstructorEntityByGymUserEntity_UserName(instructorName)).thenReturn(
                instructorEntity);
        when(authenticationService.matchInstructorCredentials(loginUserName, loginPassword)).thenReturn(true);
        when(trainingService.getInstructorListOfTrainings(any(), any(), any(), any())).thenReturn(new ArrayList<>());
        //When
        List<TrainingDto> result = instructorService.getInstructorTrainings(
                loginUserName, loginPassword, instructorName, fromDate, toDate, customerName);
        //Assert
        assertThat(result).isNotNull();
    }

    @Test
    public void shouldGetInstructorsNotAssignedToCustomerByCustomerUserName() {
        //Given
        String loginUserName = RandomStringUtils.randomAlphabetic(7);
        String loginPassword = Utils.generatePassword();
        String customerUsername = RandomStringUtils.randomAlphabetic(7);

        when(instructorRepository.findUnassignedInstructorsByCustomerUsername(customerUsername)).thenReturn(
                new ArrayList<>());
        when(authenticationService.matchInstructorCredentials(loginUserName, loginPassword)).thenReturn(true);
        //When
        List<InstructorDto> result = instructorService.getInstructorsNotAssignedToCustomerByCustomerUserName(
                loginUserName, loginPassword, customerUsername);
        //Assert
        assertThat(result).isNotNull();
    }
}
