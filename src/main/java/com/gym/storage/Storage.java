package com.gym.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym.entity.CustomerEntity;
import com.gym.entity.InstructorEntity;
import com.gym.entity.TrainingEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Getter
@Setter
public class Storage {
    private Map<Integer, CustomerEntity> customerStorage = new ConcurrentHashMap<>();
    private Map<Integer, InstructorEntity> instructorStorage = new ConcurrentHashMap<>();
    private Map<Integer, TrainingEntity> trainingStorage = new ConcurrentHashMap<>();
    @Value("${datasource.path}")
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private String filepath;

    public void updateDatasource() throws IOException {
        Map<String, Object> storageData = getStorageData();
        File file = new File(filepath);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, storageData);
    }

    public CustomerEntity addCustomer(CustomerEntity customer) {
        customerStorage.put(customer.getUserId(), customer);
        return customer;
    }

    public CustomerEntity getCustomerById(Integer customerId) {
        return customerStorage.get(customerId);
    }

    public void deleteCustomer(Integer customerId) {
        customerStorage.remove(customerId);
    }

    public Boolean checkIfCustomerUserNameExists(String userName) {
        return customerStorage.values().stream()
                .anyMatch(customer -> customer.getUserName().equals(userName));
    }

    public Set<Integer> getCustomerIds() {
        return customerStorage.keySet();
    }

    public InstructorEntity addInstructor(InstructorEntity instructor) {
        instructorStorage.put(instructor.getUserId(), instructor);
        return instructor;
    }

    public InstructorEntity getInstructorById(Integer instructorId) {
        return instructorStorage.get(instructorId);
    }

    public void deleteInstructor(Integer instructorId) {
        instructorStorage.remove(instructorId);
    }

    public Boolean checkIfInstructorUserNameExists(String userName) {
        return instructorStorage.values().stream()
                .anyMatch(instructor -> instructor.getUserName().equals(userName));
    }

    public Set<Integer> getInstructorIds() {
        return instructorStorage.keySet();
    }

    public TrainingEntity addTraining(TrainingEntity training) {
        trainingStorage.put(training.getTrainingId(), training);
        return training;
    }

    public TrainingEntity getTrainingById(Integer trainingId) {
        return trainingStorage.get(trainingId);
    }

    public Set<Integer> getTrainingIds() {
        return trainingStorage.keySet();
    }

    private Map<String, Object> getStorageData() {
        Map<String, Object> dataStorage = new ConcurrentHashMap<>();
        dataStorage.put("customerStorage", customerStorage);
        dataStorage.put("instructorStorage", instructorStorage);
        dataStorage.put("trainingStorage", trainingStorage);
        return dataStorage;
    }
}
