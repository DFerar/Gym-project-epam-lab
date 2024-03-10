package com.gym.service;

import com.gym.dto.CustomerDto;
import com.gym.dto.TrainingDto;
import com.gym.entity.CustomerEntity;
import com.gym.entity.GymUserEntity;
import com.gym.repository.CustomerRepository;
import com.gym.repository.GymUserRepository;
import com.gym.repository.TrainingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static com.gym.utils.Utils.generatePassword;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final GymUserRepository gymUserRepository;
    private final GymUserService gymUserService;
    private final TrainingRepository trainingRepository;
    private final TrainingService trainingService;
    private final AuthenticationService authenticationService;

    @Transactional
    public CustomerDto createCustomer(CustomerDto customer) {
        GymUserEntity gymUserEntity = customerDtoToUserEntity(customer);
        GymUserEntity savedUser = gymUserRepository.save(gymUserEntity);
        CustomerEntity customerEntity = customerDtoToCustomerEntity(customer, savedUser);
        CustomerEntity savedCustomer = customerRepository.save(customerEntity);
        log.info("Creating customer: {}", customer);
        return entityToDto(savedCustomer, savedUser);
    }

    @Transactional
    public CustomerDto getCustomerById(String loginUserName, String loginPassword, Integer customerId) {
        checkCredentialsMatching(loginUserName, loginPassword);
        CustomerEntity customerEntity = customerRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));
        GymUserEntity gymUserEntity = customerEntity.getGymUserEntity();
        log.info("Got customer by id: {}", customerId);
        return entityToDto(customerEntity, gymUserEntity);
    }

    @Transactional
    public CustomerDto getCustomerByUserName(String loginUserName, String loginPassword, String userName) {
        checkCredentialsMatching(loginUserName, loginPassword);
        CustomerEntity customerEntity = customerRepository.findCustomerEntityByGymUserEntityUserName(userName);
        if (customerEntity != null) {
            log.info("Got customer with username: {}", userName);
            return entityToDto(customerEntity, customerEntity.getGymUserEntity());
        } else {
            throw new NoSuchElementException("Customer not found");
        }
    }

    @Transactional
    public void changeCustomerPassword(String loginUserName, String loginPassword, Integer userId, String password) {
        checkCredentialsMatching(loginUserName, loginPassword);
        GymUserEntity gymUserEntity = gymUserRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        gymUserEntity.setPassword(password);
        gymUserRepository.save(gymUserEntity);
        log.info("Password changed on user: {}", userId);
    }

    @Transactional
    public void changeCustomersActivity(String loginUserName, String loginPassword, Integer userId,
                                        Boolean newActivity) {
        checkCredentialsMatching(loginUserName, loginPassword);
        GymUserEntity gymUserEntity = gymUserRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        gymUserEntity.setIsActive(newActivity);
        gymUserRepository.save(gymUserEntity);
        log.info("Activity changed on customer: {}", userId);
    }

    @Transactional
    public CustomerDto updateCustomer(String loginUserName, String loginPassword, CustomerDto newData) {
        checkCredentialsMatching(loginUserName, loginPassword);
        GymUserEntity updatedUser = gymUserService.updateUser(newData.getUserId(), newData.getFirstName(),
                newData.getLastName(), newData.getIsActive());

        CustomerEntity customerEntity = customerRepository.findById(newData.getId())
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));
        customerEntity.setAddress(newData.getAddress());
        CustomerEntity updatedCustomer = customerRepository.save(customerEntity);
        log.info("Customer updated: {}", newData);
        return entityToDto(updatedCustomer, updatedUser);
    }

    @Transactional
    public void deleteCustomerByUserName(String loginUserName, String loginPassword, String userName) {
        checkCredentialsMatching(loginUserName, loginPassword);
        CustomerEntity customerEntity = customerRepository.findCustomerEntityByGymUserEntityUserName(userName);
        if (customerEntity != null) {
            trainingRepository.deleteTrainingEntitiesByCustomer_GymUserEntity_UserName(userName);
            gymUserRepository.deleteGymUserEntitiesByUserName(userName);
            customerRepository.delete(customerEntity);
            log.info("Customer deleted: {}", userName);
        } else {
            throw new NoSuchElementException("Customer not found");
        }
    }

    @Transactional
    public List<TrainingDto> getCustomerTrainings(String loginUserName, String loginPassword,
                                                  String customerName, Date fromDate, Date toDate,
                                                  String instructorName,
                                                  String trainingTypeName) {
        checkCredentialsMatching(loginUserName, loginPassword);
        CustomerEntity customerEntity = customerRepository.findCustomerEntityByGymUserEntityUserName(customerName);
        if (customerEntity != null) {
            log.info("Got list of trainings of customer: {}", customerName);
            return trainingService.getCustomerListOfTrainings(customerEntity, fromDate, toDate, instructorName,
                    trainingTypeName);
        } else {
            throw new NoSuchElementException("Customer not found");
        }
    }

    private CustomerDto entityToDto(CustomerEntity savedCustomer, GymUserEntity savedUser) {
        return new CustomerDto(savedCustomer.getId(), savedCustomer.getDateOfBirth(),
                savedCustomer.getAddress(), savedUser.getId(), savedUser.getFirstName(), savedUser.getLastName(),
                savedUser.getUserName(), savedUser.getIsActive());
    }

    private CustomerEntity customerDtoToCustomerEntity(CustomerDto customerDto, GymUserEntity savedUser) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setAddress(customerDto.getAddress());
        customerEntity.setGymUserEntity(savedUser);
        customerEntity.setDateOfBirth(customerDto.getDateOfBirth());
        return customerEntity;
    }

    private GymUserEntity customerDtoToUserEntity(CustomerDto customer) {
        GymUserEntity gymUserEntity = new GymUserEntity();
        gymUserEntity.setFirstName(customer.getFirstName());
        gymUserEntity.setLastName(customer.getLastName());
        gymUserEntity.setPassword(generatePassword());
        gymUserEntity.setUserName(gymUserService.generateUniqueUserName(customer.getFirstName(),
                customer.getLastName()));
        gymUserEntity.setIsActive(customer.getIsActive());
        return gymUserEntity;
    }

    private void checkCredentialsMatching(String userName, String password) {
        if (!authenticationService.matchCustomerCredentials(userName, password)) {
            throw new SecurityException("authentication failed");
        }
    }
}
