package com.gym.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
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
            log.info("Updating customer");
            return customerRepository.updateCustomer(newData);
        } else {
            log.warn("Customer was not found");
            throw new NoSuchElementException("Customer not found");
        }
    }
}
