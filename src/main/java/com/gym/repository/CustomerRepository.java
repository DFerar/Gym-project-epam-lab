package com.gym.repository;

import com.gym.entity.CustomerEntity;
import com.gym.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@RequiredArgsConstructor
public class CustomerRepository {
    private final Storage storage;

    @SneakyThrows
    public CustomerEntity createCustomer(CustomerEntity customer) {
        CustomerEntity customerToBase = storage.addCustomer(customer);
        storage.updateDatasource();
        return customerToBase;
    }

    public CustomerEntity getCustomerById(Integer customerId) {
        return storage.getCustomerById(customerId);
    }

    @SneakyThrows
    public void deleteCustomer(Integer customerId) {
        storage.deleteCustomer(customerId);
        storage.updateDatasource();
    }

    @SneakyThrows
    public CustomerEntity updateCustomer(CustomerEntity customerToUpdate) {
        storage.updateDatasource();
        return customerToUpdate;
    }

    public boolean ifUsernameExists(String userName) {
        return storage.checkIfCustomerUserNameExists(userName);
    }

    public Set<Integer> getCustomerIds() {
        return storage.getCustomerIds();
    }
}

