package com.gym.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gym.entity.CustomerEntity;
import com.gym.entity.GymUserEntity;
import com.gym.entity.InstructorEntity;
import com.gym.repository.CustomerRepository;
import com.gym.repository.GymUserRepository;
import com.gym.repository.InstructorRepository;
import com.gym.repository.TrainingRepository;
import com.gym.utils.Utils;
import java.time.LocalDate;
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

        GymUserEntity gymUserEntity = new GymUserEntity(1L, firstName, lastName, userName, password, true, 1, null);

        CustomerEntity customerEntity = new CustomerEntity(1L, LocalDate.of(1990, 1, 1),
            address, gymUserEntity, null);

        when(gymUserService.createUser(any())).thenReturn(gymUserEntity);
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

        GymUserEntity gymUserEntity =
            new GymUserEntity(1L, firstName, lastName, userNameToDto, password, true, 1, null);

        CustomerEntity customerEntity = new CustomerEntity(1L, LocalDate.of(1990, 1, 1), address, gymUserEntity, null);

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

        GymUserEntity gymUserEntity = new GymUserEntity(userId, firstName, lastName, userName, password, true, 1, null);

        when(gymUserRepository.findByUserName(userName)).thenReturn(gymUserEntity);
        //When
        customerService.changeCustomersActivity(userName);
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

        GymUserEntity userEntityFromNewData =
            new GymUserEntity(1L, firstName, lastName, userName, password, true, 1, null);
        final CustomerEntity customerEntityFromData =
            new CustomerEntity(1L, LocalDate.of(1990, 1, 1), address, userEntityFromNewData, null);
        GymUserEntity updatedUser = new GymUserEntity();
        CustomerEntity existingCustomer = new CustomerEntity();
        updatedUser.setUserName(userName);
        existingCustomer.setGymUserEntity(updatedUser);

        when(customerRepository.findCustomerEntityByGymUserEntityUserName(userName)).thenReturn(existingCustomer);
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(existingCustomer);
        when(gymUserService.updateUser(any())).thenReturn(updatedUser);
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
        GymUserEntity gymUserEntity =
            new GymUserEntity(1L, firstName, lastName, userNameOfInstructor, password, true, 1, null);
        CustomerEntity customerEntity = new CustomerEntity(1L, LocalDate.of(1990, 1, 1),
            address, gymUserEntity, null);
        when(customerRepository.findCustomerEntityByGymUserEntityUserName(userName)).thenReturn(customerEntity);
        //When
        customerService.deleteCustomerByUserName(userName);
        //Assert
        verify(customerRepository, times(1)).delete(customerEntity);
        verify(gymUserRepository, times(1)).deleteGymUserEntitiesByUserName(userName);
        verify(trainingRepository, times(1))
            .deleteTrainingEntitiesByCustomerGymUserEntityUserName(userName);
    }

    @Test
    public void shouldChangeCustomerInstructors() {
        //Given
        final String userName = RandomStringUtils.randomAlphabetic(7);
        String instructorUsername1 = RandomStringUtils.randomAlphabetic(7);
        String instructorUsername2 = RandomStringUtils.randomAlphabetic(7);
        final List<String> usernames = List.of(instructorUsername1, instructorUsername2);
        CustomerEntity customerEntity = new CustomerEntity();
        Set<InstructorEntity> existingInstructors = new HashSet<>();
        customerEntity.setInstructors(existingInstructors);
        InstructorEntity instructor1 = new InstructorEntity();
        GymUserEntity gymUserEntity1 = new GymUserEntity();
        gymUserEntity1.setUserName(instructorUsername1);
        instructor1.setGymUserEntity(gymUserEntity1);
        InstructorEntity instructor2 = new InstructorEntity();
        GymUserEntity gymUserEntity2 = new GymUserEntity();
        gymUserEntity2.setUserName(instructorUsername2);
        instructor2.setGymUserEntity(gymUserEntity2);
        when(customerRepository.findCustomerEntityByGymUserEntityUserName(userName)).thenReturn(customerEntity);
        when(instructorRepository.findInstructorEntityByGymUserEntityUserName(instructorUsername1)).thenReturn(
            instructor1);
        when(instructorRepository.findInstructorEntityByGymUserEntityUserName(instructorUsername2)).thenReturn(
            instructor2);
        // When
        Set<InstructorEntity> result = customerService.changeCustomerInstructors(userName, usernames);
        // Assert
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(instructor1);
        assertThat(result).contains(instructor2);
        verify(customerRepository, times(1)).save(customerEntity);
    }
}
