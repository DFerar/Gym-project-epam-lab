package customerTest;


import com.gym.dto.CustomerDto;
import com.gym.dto.TrainingDto;
import com.gym.entity.CustomerEntity;
import com.gym.entity.GymUserEntity;
import com.gym.repository.CustomerRepository;
import com.gym.repository.GymUserRepository;
import com.gym.repository.TrainingRepository;
import com.gym.service.AuthenticationService;
import com.gym.service.CustomerService;
import com.gym.service.GymUserService;
import com.gym.service.TrainingService;
import com.gym.utils.Utils;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private GymUserRepository gymUserRepository;
    @Mock
    private GymUserService gymUserService;
    @Mock
    private AuthenticationService authenticationService;
    @Mock
    private TrainingRepository trainingRepository;
    @Mock
    private TrainingService trainingService;
    @InjectMocks
    private CustomerService customerService;

    @Test
    public void createCustomerTest() {
        String password = Utils.generatePassword();
        CustomerDto customerDto = new CustomerDto(1, new Date(1990, 1, 1), "testAdress",
                1, "Test", "User", "Test.User", true);

        GymUserEntity gymUserEntity = new GymUserEntity(1, "Test", "User", "Test.User", password,
                true);

        CustomerEntity customerEntity = new CustomerEntity(1, new Date(1990, 1, 1), "testAdress",
                gymUserEntity, null);

        when(gymUserRepository.save(any(GymUserEntity.class))).thenReturn(gymUserEntity);
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(customerEntity);
        when(gymUserService.generateUniqueUserName(any(String.class), any(String.class))).thenReturn("Test.user");

        CustomerDto result = customerService.createCustomer(customerDto);

        assertThat(result).isEqualTo(customerDto);
    }

    @Test
    public void getCustomerByUserNameTest() {
        String loginUserName = "user";
        String loginPassword = "password";
        String userName = "Test.User";
        String password = Utils.generatePassword();

        CustomerDto customerDto = new CustomerDto(1, new Date(1990, 1, 1), "testAdress",
                1, "Test", "User", "Test.User", true);

        GymUserEntity gymUserEntity = new GymUserEntity(1, "Test", "User", "Test.User", password,
                true);

        CustomerEntity customerEntity = new CustomerEntity(1, new Date(1990, 1, 1), "testAdress",
                gymUserEntity, null);

        when(customerRepository.findCustomerEntityByGymUserEntityUserName(userName)).thenReturn(customerEntity);
        when(authenticationService.matchCustomerCredentials(loginUserName, loginPassword)).thenReturn(true);

        CustomerDto result = customerService.getCustomerByUserName(loginUserName, loginPassword, userName);

        assertThat(result).isEqualTo(customerDto);
    }

    @Test
    public void getCustomerByIdTest() {
        String loginUserName = "user";
        String loginPassword = "password";
        String password = Utils.generatePassword();
        Integer customerId = 1;

        CustomerDto customerDto = new CustomerDto(1, new Date(1990, 1, 1), "testAdress",
                1, "Test", "User", "Test.User", true);

        GymUserEntity gymUserEntity = new GymUserEntity(1, "Test", "User", "Test.User", password,
                true);

        CustomerEntity customerEntity = new CustomerEntity(1, new Date(1990, 1, 1), "testAdress",
                gymUserEntity, null);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customerEntity));
        when(authenticationService.matchCustomerCredentials(loginUserName, loginPassword)).thenReturn(true);

        CustomerDto result = customerService.getCustomerById(loginUserName, loginPassword, customerId);

        assertThat(result).isEqualTo(customerDto);
    }

    @Test
    public void changeCustomerPasswordTest() {
        String loginUserName = "user";
        String loginPassword = "password";
        String password = Utils.generatePassword();
        Integer userId = 1;
        String newPassword = Utils.generatePassword();

        GymUserEntity gymUserEntity = new GymUserEntity(1, "Test", "User", "Test.User", password,
                true);

        when(gymUserRepository.findById(userId)).thenReturn(Optional.of(gymUserEntity));
        when(authenticationService.matchCustomerCredentials(loginUserName, loginPassword)).thenReturn(true);

        customerService.changeCustomerPassword(loginUserName, loginPassword, userId, newPassword);

        verify(gymUserRepository, times(1)).save(gymUserEntity);
        assertThat(gymUserEntity.getPassword()).isEqualTo(newPassword);
    }

    @Test
    public void changeCustomerActivity() {
        String loginUserName = "user";
        String loginPassword = "password";
        String password = Utils.generatePassword();
        Integer userId = 1;
        Boolean newActivity = false;

        GymUserEntity gymUserEntity = new GymUserEntity(1, "Test", "User", "Test.User", password,
                true);

        when(authenticationService.matchCustomerCredentials(loginUserName, loginPassword)).thenReturn(true);
        when(gymUserRepository.findById(userId)).thenReturn(Optional.of(gymUserEntity));

        customerService.changeCustomersActivity(loginUserName, loginPassword, userId, newActivity);

        verify(gymUserRepository, times(1)).save(gymUserEntity);
        assertThat(gymUserEntity.getIsActive()).isEqualTo(newActivity);
    }

    @Test
    public void updateCustomerTest() {
        String loginUserName = "user";
        String loginPassword = "password";
        String password = Utils.generatePassword();

        CustomerDto newData = new CustomerDto(1, new Date(1990, 1, 1), "testAdress",
                1, "Test", "User", "Test.User", true);

        GymUserEntity updatedUser = new GymUserEntity(1, "Test", "User", "Test.User", password,
                true);

        CustomerEntity customerEntity = new CustomerEntity(1, new Date(1990, 1, 1), "testAdress",
                updatedUser, null);


        when(authenticationService.matchCustomerCredentials(loginUserName, loginPassword)).thenReturn(true);
        when(customerRepository.findById(newData.getId())).thenReturn(Optional.of(new CustomerEntity()));
        when(gymUserService.updateUser(anyInt(), anyString(), anyString(), anyBoolean())).thenReturn(updatedUser);
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(customerEntity);

        CustomerDto result = customerService.updateCustomer(loginUserName, loginPassword, newData);

        assertThat(result).isEqualTo(newData);
    }

    @Test
    public void deleteCustomerByUserName() {
        String loginUserName = "user";
        String loginPassword = "password";
        String userName = "Test.User";
        String password = Utils.generatePassword();
        GymUserEntity gymUserEntity = new GymUserEntity(1, "Test", "User", "Test.User", password,
                true);
        CustomerEntity customerEntity = new CustomerEntity(1, new Date(1990, 1, 1), "testAdress",
                gymUserEntity, null);

        when(customerRepository.findCustomerEntityByGymUserEntityUserName(userName)).thenReturn(customerEntity);
        when(authenticationService.matchCustomerCredentials(loginUserName, loginPassword)).thenReturn(true);

        customerService.deleteCustomerByUserName(loginUserName, loginPassword, userName);

        verify(customerRepository, times(1)).delete(customerEntity);
        verify(gymUserRepository, times(1)).deleteGymUserEntitiesByUserName(userName);
        verify(trainingRepository, times(1)).deleteTrainingEntitiesByCustomer_GymUserEntity_UserName(userName);
    }

    @Test
    public void getCustomerTrainingsTest() {
        String loginUserName = "username";
        String loginPassword = "password";
        String customerName = "Test.User";
        Date fromDate = Date.valueOf("2022-01-01");
        Date toDate = Date.valueOf("2022-12-31");
        String instructorName = "Instructor";
        String trainingTypeName = "CARDIO";

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(1);
        customerEntity.setAddress("123 Main St");

        when(customerRepository.findCustomerEntityByGymUserEntityUserName(customerName)).thenReturn(customerEntity);
        when(authenticationService.matchCustomerCredentials(loginUserName, loginPassword)).thenReturn(true);
        when(trainingService.getCustomerListOfTrainings(any(), any(), any(), any(), any())).thenReturn(new ArrayList<>());

        List<TrainingDto> result = customerService.getCustomerTrainings(
                loginUserName, loginPassword, customerName, fromDate, toDate, instructorName, trainingTypeName);

        assertThat(result).isNotNull();
    }
}
