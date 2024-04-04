package com.gym.service;

import com.gym.entity.CustomerEntity;
import com.gym.entity.GymUserEntity;
import com.gym.entity.InstructorEntity;
import com.gym.exceptionHandler.CustomerNotFoundException;
import com.gym.exceptionHandler.UserNotFoundException;
import com.gym.repository.CustomerRepository;
import com.gym.repository.GymUserRepository;
import com.gym.repository.InstructorRepository;
import com.gym.repository.TrainingRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final GymUserRepository gymUserRepository;
    private final GymUserService gymUserService;
    private final TrainingRepository trainingRepository;
    private final InstructorRepository instructorRepository;

    @Transactional
    public CustomerEntity createCustomer(CustomerEntity customerEntity, GymUserEntity gymUserEntity) {
        GymUserEntity savedUser = gymUserRepository.save(gymUserEntity);
        customerEntity.setGymUserEntity(savedUser);
        CustomerEntity savedCustomer = customerRepository.save(customerEntity);
        log.info("Creating customer: {}", savedCustomer);
        return savedCustomer;
    }

    @Transactional
    public CustomerEntity getCustomerByUserName(String userName) {
        CustomerEntity customerEntity = customerRepository.findCustomerEntityByGymUserEntityUserName(userName);
        if (customerEntity == null) {
            throw new CustomerNotFoundException("Customer not found");
        }
        log.info("Got customer with username: {}", userName);
        return customerEntity;
    }

    @Transactional
    public void changeCustomersActivity(String username) {
        GymUserEntity gymUserEntity = gymUserRepository.findByUserName(username);
        if (gymUserEntity == null) {
            throw new UserNotFoundException("User not found");
        }
        gymUserEntity.setIsActive(!gymUserEntity.getIsActive());
        gymUserRepository.save(gymUserEntity);
        log.info("Activity changed on customer: {}", gymUserEntity);
    }

    @Transactional
    public CustomerEntity updateCustomer(GymUserEntity userEntityFromData, CustomerEntity customerEntityFromData) {
        GymUserEntity updatedUser = gymUserService.updateUser(userEntityFromData);

        CustomerEntity customerEntity = customerRepository.findCustomerEntityByGymUserEntityUserName(
                updatedUser.getUserName());
        if (customerEntity == null) {
            throw new CustomerNotFoundException("Customer not found");
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
            throw new CustomerNotFoundException("Customer not found");
        }
        trainingRepository.deleteTrainingEntitiesByCustomerGymUserEntityUserName(userName);
        gymUserRepository.deleteGymUserEntitiesByUserName(userName);
        customerRepository.delete(customerEntity);
        log.info("Customer deleted: {}", userName);
    }
    @Transactional
    public Set<InstructorEntity> changeCustomerInstructors(String userName, List<String> usernames) {
        CustomerEntity customerEntity = customerRepository.findCustomerEntityByGymUserEntityUserName(userName);
        if (customerEntity == null) {
            throw new CustomerNotFoundException("Customer not found");
        }
        Set<InstructorEntity> instructorEntities = usernames.stream()
                .map(instructorRepository::findInstructorEntityByGymUserEntityUserName)
                .collect(Collectors.toSet());
        Set<InstructorEntity> existingInstructorEntities = customerEntity.getInstructors();
        existingInstructorEntities.addAll(instructorEntities);
        customerEntity.setInstructors(existingInstructorEntities);
        customerRepository.save(customerEntity);
        return customerEntity.getInstructors();
    }
}
