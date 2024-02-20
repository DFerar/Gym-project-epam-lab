package com.gym.customer;

import com.gym.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;

import static com.gym.utils.Utils.*;

@Repository
@RequiredArgsConstructor
public class CustomerRepository {
    private final Storage storage;


    public Customer createCustomer(Customer customer) {
        Integer userId = getLastMapObjectId(storage.getCustomerStorage().keySet()) + 1;
        customer.setPassword(generatePassword());
        customer.setUserId(userId);
        String userName = generateUsername(customer.getFirstName(), customer.getLastName());
        if (!ifUsernameExists(userName)) {
            customer.setUserName(userName);
        } else {
            String uniqueUserName = userName + userId;
            customer.setUserName(uniqueUserName);
        }
        storage.getCustomerStorage().put(userId, customer);
        try {
            storage.updateDatasource(storage.getStorageData());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return customer;
    }

    public Customer getCustomerById(Integer customerId) {
        return storage.getCustomerStorage().get(customerId);
    }

    public void deleteCustomer(Integer customerId) {
        storage.getCustomerStorage().remove(customerId);
        try {
            storage.updateDatasource(storage.getStorageData());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Customer updateCustomer(Customer newData) {
        Customer customerToUpdate = getCustomerById(newData.getUserId());
        String newUserName = generateUsername(newData.getFirstName(), newData.getLastName());
        if (!ifUsernameExists(newUserName)) {
            customerToUpdate.setUserName(newUserName);
        } else {
            String uniqueUserName = newUserName + newData.getUserId();
            customerToUpdate.setUserName(uniqueUserName);
        }
        customerToUpdate.setFirstName(newData.getFirstName());
        customerToUpdate.setLastName(newData.getLastName());
        customerToUpdate.setIsActive(newData.getIsActive());
        customerToUpdate.setAddress(newData.getAddress());
        customerToUpdate.setDateOfBirth(newData.getDateOfBirth());
        try {
            storage.updateDatasource(storage.getStorageData());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return customerToUpdate;
    }

    private boolean ifUsernameExists(String userName) {
        return storage.getCustomerStorage().values().stream()
                .anyMatch(customer -> customer.getUserName().equals(userName));
    }
}
