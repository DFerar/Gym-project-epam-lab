package com.gym.service;

import com.gym.entity.CustomerEntity;
import com.gym.entity.GymUserEntity;
import com.gym.entity.InstructorEntity;
import com.gym.exception.CustomerNotFoundException;
import com.gym.exception.UserNotFoundException;
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

    /**
     * Creates a new customer and saves it in the database, logging the process.
     *
     * @param customerEntity CustomerEntity object to be saved.
     * @param gymUserEntity  GymUserEntity object to be bound to the customer.
     * @return CustomerEntity representing the newly created and saved customer.
     */
    @Transactional
    public CustomerEntity createCustomer(CustomerEntity customerEntity, GymUserEntity gymUserEntity) {
        GymUserEntity savedUser = gymUserService.createUser(gymUserEntity);
        customerEntity.setGymUserEntity(savedUser);
        CustomerEntity savedCustomer = customerRepository.save(customerEntity);
        log.info("Creating customer: {}", savedCustomer);
        return savedCustomer;
    }

    /**
     * Retrieves a customer based on a provided username.
     *
     * @param userName The username associated with the customer.
     * @return CustomerEntity associated with the username.
     * @throws CustomerNotFoundException When a customer with the given username is not found.
     */
    @Transactional
    public CustomerEntity getCustomerByUserName(String userName) {
        CustomerEntity customerEntity = customerRepository.findCustomerEntityByGymUserEntityUserName(userName);
        if (customerEntity == null) {
            throw new CustomerNotFoundException("Customer not found");
        }
        log.info("Got customer with username: {}", userName);
        return customerEntity;
    }

    /**
     * Changes the activity status of a customer.
     *
     * @param username The username associated with the customer.
     * @throws UserNotFoundException When a user with the given username is not found.
     */
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

    /**
     * Updates customer and userEntity details.
     *
     * @param userEntityFromData     GymUserEntity object containing the updated details.
     * @param customerEntityFromData CustomerEntity object containing the updated details.
     * @return CustomerEntity representing the updated customer.
     * @throws CustomerNotFoundException When a customer with the given username is not found.
     */
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

    /**
     * Deletes a customer based on a provided username, logging the process.
     *
     * @param userName The username associated with the customer to be deleted.
     * @throws CustomerNotFoundException When a customer with the given username is not found.
     */
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

    /**
     * Changes the instructors associated with a customer based on provided username.
     *
     * @param userName  The username of the customer.
     * @param usernames A list of usernames of the instructors.
     * @return a set of updated instructors.
     * @throws CustomerNotFoundException When a customer with the given username is not found.
     */
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
