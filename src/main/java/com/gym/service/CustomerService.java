package com.gym.service;

import com.gym.dto.CustomerDto;
import com.gym.entity.CustomerEntity;
import com.gym.entity.GymUserEntity;
import com.gym.entity.InstructorEntity;
import com.gym.repository.CustomerRepository;
import com.gym.repository.GymUserRepository;
import com.gym.repository.InstructorRepository;
import com.gym.repository.TrainingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final GymUserRepository gymUserRepository;
    private final GymUserService gymUserService;
    private final TrainingRepository trainingRepository;
    private final InstructorRepository instructorRepository;
    private final AuthenticationService authenticationService;

    @Transactional
    public CustomerEntity createCustomer(CustomerEntity customerEntity, GymUserEntity gymUserEntity) {
        GymUserEntity savedUser = gymUserRepository.save(gymUserEntity);
        customerEntity.setGymUserEntity(savedUser);
        CustomerEntity savedCustomer = customerRepository.save(customerEntity);
        log.info("Creating customer: {}", savedCustomer);
        return savedCustomer;
    }

    @Transactional
    public CustomerDto getCustomerById(String loginUserName, String loginPassword, Long customerId) {
        checkCredentialsMatching(loginUserName, loginPassword);
        CustomerEntity customerEntity = customerRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));
        GymUserEntity gymUserEntity = customerEntity.getGymUserEntity();
        log.info("Got customer by id: {}", customerId);
        return entityToDto(customerEntity, gymUserEntity);
    }

    @Transactional
    public CustomerEntity getCustomerByUserName(String userName) {
        CustomerEntity customerEntity = customerRepository.findCustomerEntityByGymUserEntityUserName(userName);
        if (customerEntity == null) {
            throw new NoSuchElementException("Customer not found");
        }
        log.info("Got customer with username: {}", userName);
        return customerEntity;
    }

    @Transactional
    public void changeCustomerPassword(String loginUserName, String loginPassword, Long userId, String password) {
        checkCredentialsMatching(loginUserName, loginPassword);
        GymUserEntity gymUserEntity = gymUserRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        gymUserEntity.setPassword(password);
        gymUserRepository.save(gymUserEntity);
        log.info("Password changed on user: {}", userId);
    }

    @Transactional
    public void changeCustomersActivity(String username, Boolean newActivity) {
        GymUserEntity gymUserEntity = gymUserRepository.findByUserName(username);
        if (gymUserEntity == null) {
            throw new NoSuchElementException("User not found");
        }
        gymUserEntity.setIsActive(newActivity);
        gymUserRepository.save(gymUserEntity);
        log.info("Activity changed on customer: {}", gymUserEntity);
    }

    @Transactional
    public CustomerEntity updateCustomer(GymUserEntity userEntityFromData, CustomerEntity customerEntityFromData) {
        GymUserEntity updatedUser = gymUserService.updateUser(userEntityFromData);

        CustomerEntity customerEntity = customerRepository.findCustomerEntityByGymUserEntityUserName(
                userEntityFromData.getUserName());
        if (customerEntity == null) {
            throw new NoSuchElementException("Customer not found");
        }
        customerEntity.setAddress(customerEntityFromData.getAddress());
        customerEntity.setDateOfBirth(customerEntityFromData.getDateOfBirth());
        customerEntity.setGymUserEntity(updatedUser);
        CustomerEntity updatedCustomer = customerRepository.save(customerEntity);
        log.info("Customer updated: {}", updatedCustomer);
        return updatedCustomer;
    }

    @Transactional
    public void deleteCustomerByUserName(String userName) {
        CustomerEntity customerEntity = customerRepository.findCustomerEntityByGymUserEntityUserName(userName);
        if (customerEntity == null) {
            throw new NoSuchElementException("Customer not found");
        }
        trainingRepository.deleteTrainingEntitiesByCustomerGymUserEntityUserName(userName);
        gymUserRepository.deleteGymUserEntitiesByUserName(userName);
        customerRepository.delete(customerEntity);
        log.info("Customer deleted: {}", userName);
    }

    public Set<InstructorEntity> changeCustomerInstructors(String userName, List<String> usernames) {
        CustomerEntity customerEntity = customerRepository.findCustomerEntityByGymUserEntityUserName(userName);
        Set<InstructorEntity> instructorEntities = usernames.stream()
                .map(instructorRepository::findInstructorEntityByGymUserEntityUserName)
                .collect(Collectors.toSet());
        customerEntity.setInstructors(instructorEntities);
        return instructorEntities;
    }
}
