package customerTest;


import com.gym.entity.CustomerEntity;
import com.gym.entity.GymUserEntity;
import com.gym.entity.InstructorEntity;
import com.gym.repository.CustomerRepository;
import com.gym.repository.GymUserRepository;
import com.gym.repository.InstructorRepository;
import com.gym.repository.TrainingRepository;
import com.gym.service.CustomerService;
import com.gym.service.GymUserService;
import com.gym.utils.Utils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private GymUserRepository gymUserRepository;
    @Mock
    private InstructorRepository instructorRepository;
    @Mock
    private TrainingRepository trainingRepository;
    @Mock
    private GymUserService gymUserService;
    @InjectMocks
    private CustomerService customerService;

    @Test
    public void shouldCreateCustomer() {
        //Given
        String password = Utils.generatePassword();
        String address = RandomStringUtils.randomAlphabetic(7);
        String firstName = RandomStringUtils.randomAlphabetic(7);
        String lastName = RandomStringUtils.randomAlphabetic(7);
        String userName = RandomStringUtils.randomAlphabetic(7);

        GymUserEntity gymUserEntity = new GymUserEntity(1L, firstName, lastName, userName, password,
                true);

        CustomerEntity customerEntity = new CustomerEntity(1L, Date.valueOf("1990-01-01"), address,
                gymUserEntity, null);

        when(gymUserRepository.save(any(GymUserEntity.class))).thenReturn(gymUserEntity);
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(customerEntity);
        //When
        CustomerEntity result = customerService.createCustomer(customerEntity, gymUserEntity);
        //Assert
        assertThat(result).isEqualTo(customerEntity);
    }

    @Test
    public void shouldGetCustomerByUserName() {
        //Given
        String userName = RandomStringUtils.randomAlphabetic(7);
        String password = Utils.generatePassword();
        String address = RandomStringUtils.randomAlphabetic(7);
        String firstName = RandomStringUtils.randomAlphabetic(7);
        String lastName = RandomStringUtils.randomAlphabetic(7);
        String userNameToDto = RandomStringUtils.randomAlphabetic(7);

        GymUserEntity gymUserEntity = new GymUserEntity(1L, firstName, lastName, userNameToDto, password,
                true);

        CustomerEntity customerEntity = new CustomerEntity(1L, Date.valueOf("1990-01-01"), address,
                gymUserEntity, null);

        when(customerRepository.findCustomerEntityByGymUserEntityUserName(userName)).thenReturn(customerEntity);
        //When
        CustomerEntity result = customerService.getCustomerByUserName(userName);
        //Assert
        assertThat(result).isEqualTo(customerEntity);
    }

    @Test
    public void shouldChangeCustomerActivity() {
        //Given
        String password = Utils.generatePassword();
        Long userId = 1L;
        Boolean newActivity = false;
        String firstName = RandomStringUtils.randomAlphabetic(7);
        String lastName = RandomStringUtils.randomAlphabetic(7);
        String userName = RandomStringUtils.randomAlphabetic(7);

        GymUserEntity gymUserEntity = new GymUserEntity(userId, firstName, lastName, userName, password,
                true);

        when(gymUserRepository.findByUserName(userName)).thenReturn(gymUserEntity);
        //When
        customerService.changeCustomersActivity(userName, newActivity);
        //Assert
        verify(gymUserRepository, times(1)).save(gymUserEntity);
        assertThat(gymUserEntity.getIsActive()).isEqualTo(newActivity);
    }

    @Test
    public void shouldUpdateCustomer() {
        //Given
        String password = Utils.generatePassword();
        String address = RandomStringUtils.randomAlphabetic(7);
        String firstName = RandomStringUtils.randomAlphabetic(7);
        String lastName = RandomStringUtils.randomAlphabetic(7);
        String userName = RandomStringUtils.randomAlphabetic(7);

        GymUserEntity userEntityFromNewData = new GymUserEntity(1L, firstName, lastName, userName, password,
                true);

        CustomerEntity customerEntityFromData = new CustomerEntity(1L, Date.valueOf("1990-01-01"), address,
                userEntityFromNewData, null);

        GymUserEntity updatedUser = new GymUserEntity();
        CustomerEntity existingCustomer = new CustomerEntity();
        existingCustomer.setGymUserEntity(updatedUser);

        when(customerRepository.findCustomerEntityByGymUserEntityUserName(userName)).thenReturn(existingCustomer);
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(existingCustomer);
        //When
        CustomerEntity updatedCustomer = customerService.updateCustomer(userEntityFromNewData, customerEntityFromData);
        //Assert
        assertThat(updatedCustomer).isEqualTo(existingCustomer);
    }

    @Test
    public void shouldDeleteCustomerByUserName() {
        //Given
        String userName = RandomStringUtils.randomAlphabetic(7);
        String password = Utils.generatePassword();
        String address = RandomStringUtils.randomAlphabetic(7);
        String firstName = RandomStringUtils.randomAlphabetic(7);
        String lastName = RandomStringUtils.randomAlphabetic(7);
        String userNameOfInstructor = RandomStringUtils.randomAlphabetic(7);
        GymUserEntity gymUserEntity = new GymUserEntity(1L, firstName, lastName, userNameOfInstructor, password,
                true);
        CustomerEntity customerEntity = new CustomerEntity(1L, Date.valueOf("1990-01-01"), address,
                gymUserEntity, null);

        when(customerRepository.findCustomerEntityByGymUserEntityUserName(userName)).thenReturn(customerEntity);
        //When
        customerService.deleteCustomerByUserName(userName);
        //Assert
        verify(customerRepository, times(1)).delete(customerEntity);
        verify(gymUserRepository, times(1)).deleteGymUserEntitiesByUserName(userName);
        verify(trainingRepository, times(1)).deleteTrainingEntitiesByCustomerGymUserEntityUserName(userName);
    }

    @Test
    public void shouldChangeCustomerInstructors() {
        //Given
        String userName = RandomStringUtils.randomAlphabetic(7);
        String password = Utils.generatePassword();
        String address = RandomStringUtils.randomAlphabetic(7);
        String firstName = RandomStringUtils.randomAlphabetic(7);
        String lastName = RandomStringUtils.randomAlphabetic(7);
        List<String> usernames = List.of(RandomStringUtils.randomAlphabetic(7),
                RandomStringUtils.randomAlphabetic(7));

        GymUserEntity gymUserEntity = new GymUserEntity(1L, firstName, lastName, userName, password,
                true);
        CustomerEntity customerEntity = new CustomerEntity(1L, Date.valueOf("1990-01-01"), address,
                gymUserEntity, null);

        when(customerRepository.findCustomerEntityByGymUserEntityUserName(userName)).thenReturn(customerEntity);
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(customerEntity);

        Set<InstructorEntity> instructorEntities = usernames.stream()
                .map(username -> {
                    InstructorEntity instructorEntity = new InstructorEntity();
                    GymUserEntity gymUser = new GymUserEntity();
                    gymUser.setUserName(username);
                    instructorEntity.setGymUserEntity(gymUser);
                    return instructorEntity;
                })
                .collect(Collectors.toSet());

        when(instructorRepository.findInstructorEntityByGymUserEntityUserName(any(String.class)))
                .thenAnswer(invocation -> {
                    String username = invocation.getArgument(0);
                    InstructorEntity instructorEntity = new InstructorEntity();
                    GymUserEntity gymUser = new GymUserEntity();
                    gymUser.setUserName(username);
                    instructorEntity.setGymUserEntity(gymUser);
                    return instructorEntity;
                });
        //When
        Set<InstructorEntity> result = customerService.changeCustomerInstructors(userName, usernames);
        //Assertions
        assertThat(result.size()).isEqualTo(instructorEntities.size());
        assertThat(result).isEqualTo(instructorEntities);
    }
}
