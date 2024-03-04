package com.gym.service;

import static com.gym.utils.Utils.generatePassword;
import static com.gym.utils.Utils.generateUsername;

import com.gym.dto.CustomerDto;
import com.gym.entity.CustomerEntity;
import com.gym.entity.GymUserEntity;
import com.gym.repository.CustomerRepository;
import com.gym.repository.GymUserRepository;
import jakarta.transaction.Transactional;
import java.util.NoSuchElementException;
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

    @Transactional
    public CustomerDto createCustomer(CustomerDto customer) {
        GymUserEntity gymUserEntity = customerDtoToUserEntity(customer);
        GymUserEntity savedUser = gymUserRepository.save(gymUserEntity);
        CustomerEntity customerEntity = customerDtoToCustomerEntity(customer, savedUser);
        CustomerEntity savedCustomer = customerRepository.save(customerEntity);
        log.info("Creating customer: {}", customer);
        return entityToDto(savedCustomer, savedUser);
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
        return gymUserEntity;
    }


    public CustomerDto getCustomerById(Integer customerId) {
        CustomerEntity customerEntity = customerRepository.findById(customerId)
            .orElseThrow(() -> new NoSuchElementException("Customer not found"));
        GymUserEntity gymUserEntity = gymUserRepository.findById(customerEntity.getGymUserEntity().getId())
            .orElseThrow(() -> new NoSuchElementException("User not found"));
        return entityToDto(customerEntity, gymUserEntity);
    }

    public CustomerEntity getCustomerByUserName(String userName) {

    }



    public void deleteCustomer(Integer customerId) {
        if (customerRepository.getCustomerById(customerId) != null) {
            customerRepository.deleteCustomer(customerId);
            log.info("Customer with ID was deleted: {}", customerId);
        } else {
            log.warn("Customer was not found with ID: {}", customerId);
            throw new NoSuchElementException("Customer not found");
        }
    }

    public CustomerEntity updateCustomer(CustomerEntity newData) {
        if (customerRepository.getCustomerById(newData.getUserId()) != null) {
            CustomerEntity customerToUpdate = getCustomerById(newData.getUserId());
            String newUserName = generateUniqueCustomerName(newData.getFirstName(), newData.getLastName(),
                    customerToUpdate.getUserId());
            customerToUpdate.setUserName(newUserName);
            customerToUpdate.setFirstName(newData.getFirstName());
            customerToUpdate.setLastName(newData.getLastName());
            customerToUpdate.setIsActive(newData.getIsActive());
            customerToUpdate.setAddress(newData.getAddress());
            customerToUpdate.setDateOfBirth(newData.getDateOfBirth());
            log.info("Updating customer with ID:{}", newData.getUserId());
            return customerRepository.updateCustomer(customerToUpdate);
        } else {
            log.warn("Customer was not found");
            throw new NoSuchElementException("Customer not found");
        }
    }

    private String generateUniqueCustomerName(String firstName, String lastName, Integer userId) {
        String userName = generateUsername(firstName, lastName);
        if (!(customerRepository.ifUsernameExists(userName))) {
            return userName;
        } else {
            return userName + userId;
        }
    }
}
