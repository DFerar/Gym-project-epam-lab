package com.gym.service;

import static com.gym.utils.Utils.generatePassword;
import static com.gym.utils.Utils.generateUsername;
import static com.gym.utils.Utils.getLastMapObjectId;

import com.gym.entity.CustomerEntity;
import com.gym.repository.CustomerRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerEntity createCustomer(CustomerEntity customer) {
        int userId = getLastMapObjectId(customerRepository.getCustomerIds()) + 1;
        customer.setPassword(generatePassword());
        customer.setUserId(userId);
        String userName = generateUniqueCustomerName(customer.getFirstName(), customer.getLastName(), userId);
        customer.setUserName(userName);
        log.info("Creating customer: {}", customer);
        return customerRepository.createCustomer(customer);
    }


    public CustomerEntity getCustomerById(Integer customerId) {
        CustomerEntity customer = customerRepository.getCustomerById(customerId);
        if (customer != null) {
            log.info("Getting customer by ID {}", customerId);
            return customer;
        } else {
            log.warn("Customer not found with ID: {} ", customerId);
            throw new NoSuchElementException("Customer not found");
        }
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
