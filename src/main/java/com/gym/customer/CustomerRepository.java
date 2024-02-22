package com.gym.customer;

import com.gym.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CustomerRepository {
    private final Storage storage;

    @SneakyThrows
    public Customer createCustomer(Customer customer) {
        storage.getCustomerStorage().put(customer.getUserId(), customer);
        storage.updateDatasource();
        return customer;
    }

    public Customer getCustomerById(Integer customerId) {
        return storage.getCustomerStorage().get(customerId);
    }

    @SneakyThrows
    public void deleteCustomer(Integer customerId) {
        storage.getCustomerStorage().remove(customerId);
        storage.updateDatasource();
    }

    @SneakyThrows
    public Customer updateCustomer(Customer customerToUpdate) {
        storage.updateDatasource();
        return customerToUpdate;
    }

    public boolean ifUsernameExists(String userName) {
        return storage.getCustomerStorage().values().stream()
                .anyMatch(customer -> customer.getUserName().equals(userName));
    }

    public Map<Integer, Customer> getCustomerMap() {
        return storage.getCustomerStorage();
    }
}

