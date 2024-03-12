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
    public void shouldCreateCustomer() {
        //Given
        String password = Utils.generatePassword();
        CustomerDto customerDto = new CustomerDto(1, Date.valueOf("1990-01-01"), "testAddress",
                1, "Test", "User", "Test.User", true);

        GymUserEntity gymUserEntity = new GymUserEntity(1, "Test", "User", "Test.User", password,
                true);

        CustomerEntity customerEntity = new CustomerEntity(1, Date.valueOf("1990-01-01"), "testAddress",
                gymUserEntity, null);

        when(gymUserRepository.save(any(GymUserEntity.class))).thenReturn(gymUserEntity);
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(customerEntity);
        when(gymUserService.generateUniqueUserName(any(String.class), any(String.class))).thenReturn("Test.user");
        //When
        CustomerDto result = customerService.createCustomer(customerDto);
        //Assert
        assertThat(result).isEqualTo(customerDto);
    }

    @Test
    public void shouldGetCustomerByUserName() {
        //Given
        String loginUserName = RandomStringUtils.randomAlphabetic(7);
        String loginPassword = Utils.generatePassword();
        String userName = RandomStringUtils.randomAlphabetic(7);
        String password = Utils.generatePassword();

        CustomerDto customerDto = new CustomerDto(1, Date.valueOf("1990-01-01"), "testAddress",
                1, "Test", "User", "Test.User", true);

        GymUserEntity gymUserEntity = new GymUserEntity(1, "Test", "User", "Test.User", password,
                true);

        CustomerEntity customerEntity = new CustomerEntity(1, Date.valueOf("1990-01-01"), "testAddress",
                gymUserEntity, null);

        when(customerRepository.findCustomerEntityByGymUserEntityUserName(userName)).thenReturn(customerEntity);
        when(authenticationService.matchCustomerCredentials(loginUserName, loginPassword)).thenReturn(true);
        //When
        CustomerDto result = customerService.getCustomerByUserName(loginUserName, loginPassword, userName);
        //Assert
        assertThat(result).isEqualTo(customerDto);
    }

    @Test
    public void shouldGetCustomerById() {
        //Given
        String loginUserName = RandomStringUtils.randomAlphabetic(7);
        String loginPassword = Utils.generatePassword();
        String password = Utils.generatePassword();
        Integer customerId = 1;

        CustomerDto customerDto = new CustomerDto(customerId, Date.valueOf("1990-01-01"), "testAddress",
                1, "Test", "User", "Test.User", true);

        GymUserEntity gymUserEntity = new GymUserEntity(1, "Test", "User", "Test.User", password,
                true);

        CustomerEntity customerEntity = new CustomerEntity(customerId, Date.valueOf("1990-01-01"), "testAddress",
                gymUserEntity, null);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customerEntity));
        when(authenticationService.matchCustomerCredentials(loginUserName, loginPassword)).thenReturn(true);
        //When
        CustomerDto result = customerService.getCustomerById(loginUserName, loginPassword, customerId);
        //Assert
        assertThat(result).isEqualTo(customerDto);
    }

    @Test
    public void shouldChangeCustomerPassword() {
        //Given
        String loginUserName = RandomStringUtils.randomAlphabetic(7);
        String loginPassword = Utils.generatePassword();
        String password = Utils.generatePassword();
        Integer userId = 1;
        String newPassword = Utils.generatePassword();

        GymUserEntity gymUserEntity = new GymUserEntity(userId, "Test", "User", "Test.User", password,
                true);

        when(gymUserRepository.findById(userId)).thenReturn(Optional.of(gymUserEntity));
        when(authenticationService.matchCustomerCredentials(loginUserName, loginPassword)).thenReturn(true);
        //When
        customerService.changeCustomerPassword(loginUserName, loginPassword, userId, newPassword);
        //Assert
        verify(gymUserRepository, times(1)).save(gymUserEntity);
        assertThat(gymUserEntity.getPassword()).isEqualTo(newPassword);
    }

    @Test
    public void shouldChangeCustomerActivity() {
        //Given
        String loginUserName = RandomStringUtils.randomAlphabetic(7);
        String loginPassword = Utils.generatePassword();
        String password = Utils.generatePassword();
        Integer userId = 1;
        Boolean newActivity = false;

        GymUserEntity gymUserEntity = new GymUserEntity(1, "Test", "User", "Test.User", password,
                true);

        when(authenticationService.matchCustomerCredentials(loginUserName, loginPassword)).thenReturn(true);
        when(gymUserRepository.findById(userId)).thenReturn(Optional.of(gymUserEntity));
        //When
        customerService.changeCustomersActivity(loginUserName, loginPassword, userId, newActivity);
        //Assert
        verify(gymUserRepository, times(1)).save(gymUserEntity);
        assertThat(gymUserEntity.getIsActive()).isEqualTo(newActivity);
    }

    @Test
    public void shouldUpdateCustomer() {
        //Given
        String loginUserName = RandomStringUtils.randomAlphabetic(7);
        String loginPassword = RandomStringUtils.randomAlphabetic(7);
        String password = Utils.generatePassword();

        CustomerDto newData = new CustomerDto(1, Date.valueOf("1990-01-01"), "testAddress",
                1, "Test", "User", "Test.User", true);

        GymUserEntity updatedUser = new GymUserEntity(1, "Test", "User", "Test.User", password,
                true);

        CustomerEntity customerEntity = new CustomerEntity(1, Date.valueOf("1990-01-01"), "testAddress",
                updatedUser, null);


        when(authenticationService.matchCustomerCredentials(loginUserName, loginPassword)).thenReturn(true);
        when(customerRepository.findById(newData.getId())).thenReturn(Optional.of(new CustomerEntity()));
        when(gymUserService.updateUser(anyInt(), anyString(), anyString(), anyBoolean())).thenReturn(updatedUser);
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(customerEntity);
        //When
        CustomerDto result = customerService.updateCustomer(loginUserName, loginPassword, newData);
        //Assert
        assertThat(result).isEqualTo(newData);
    }

    @Test
    public void shouldDeleteCustomerByUserName() {
        //Given
        String loginUserName = RandomStringUtils.randomAlphabetic(7);
        String loginPassword = Utils.generatePassword();
        String userName = RandomStringUtils.randomAlphabetic(7);
        String password = Utils.generatePassword();
        GymUserEntity gymUserEntity = new GymUserEntity(1, "Test", "User", "Test.User", password,
                true);
        CustomerEntity customerEntity = new CustomerEntity(1, Date.valueOf("1990-01-01"), "testAddress",
                gymUserEntity, null);

        when(customerRepository.findCustomerEntityByGymUserEntityUserName(userName)).thenReturn(customerEntity);
        when(authenticationService.matchCustomerCredentials(loginUserName, loginPassword)).thenReturn(true);
        //When
        customerService.deleteCustomerByUserName(loginUserName, loginPassword, userName);
        //Assert
        verify(customerRepository, times(1)).delete(customerEntity);
        verify(gymUserRepository, times(1)).deleteGymUserEntitiesByUserName(userName);
        verify(trainingRepository, times(1)).deleteTrainingEntitiesByCustomer_GymUserEntity_UserName(userName);
    }

    @Test
    public void shouldGetCustomerTrainings() {
        //Given
        String loginUserName = RandomStringUtils.randomAlphabetic(7);
        String loginPassword = Utils.generatePassword();
        String customerName = RandomStringUtils.randomAlphabetic(7);
        Date fromDate = Date.valueOf("2022-01-01");
        Date toDate = Date.valueOf("2024-12-31");
        String instructorName = RandomStringUtils.randomAlphabetic(7);
        String trainingTypeName = RandomStringUtils.randomAlphabetic(7);

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(1);
        customerEntity.setAddress("123 Main St");

        when(customerRepository.findCustomerEntityByGymUserEntityUserName(customerName)).thenReturn(customerEntity);
        when(authenticationService.matchCustomerCredentials(loginUserName, loginPassword)).thenReturn(true);
        when(trainingService.getCustomerListOfTrainings(any(), any(), any(), any(), any())).thenReturn(new ArrayList<>());
        //When
        List<TrainingDto> result = customerService.getCustomerTrainings(
                loginUserName, loginPassword, customerName, fromDate, toDate, instructorName, trainingTypeName);
        //Assert
        assertThat(result).isNotNull();
    }
}
