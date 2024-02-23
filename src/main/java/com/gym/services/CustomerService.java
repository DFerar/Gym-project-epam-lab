package com.gym.services;

import com.gym.entities.Customer;
import com.gym.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static com.gym.utils.Utils.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        Integer userId = getLastMapObjectId(customerRepository.getCustomerMap().keySet()) + 1;
        customer.setPassword(generatePassword());
        customer.setUserId(userId);
        String userName = generateUniqueCustomerName(customer.getFirstName(), customer.getLastName(), userId);
        customer.setUserName(userName);
        log.info("Creating customer: {}", customer);
        return customerRepository.createCustomer(customer);
    }


    public Customer getCustomerById(Integer customerId) {
        Customer customer = customerRepository.getCustomerById(customerId);
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

    public Customer updateCustomer(Customer newData) {
        if (customerRepository.getCustomerById(newData.getUserId()) != null) {
            Customer customerToUpdate = getCustomerById(newData.getUserId());
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
