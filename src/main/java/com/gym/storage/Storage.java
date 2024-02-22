package com.gym.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym.customer.Customer;
import com.gym.training.Training;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Getter
@Setter
public class Storage {
    private Map<Integer, Customer> customerStorage = new ConcurrentHashMap<>();
    private Map<Integer, com.gym.instructor.Instructor> instructorStorage = new ConcurrentHashMap<>();
    private Map<Integer, Training> trainingStorage = new ConcurrentHashMap<>();
    @Value("${datasource.path}")
    private String filepath;

    public void updateDatasource() throws IOException {
        Map<String, Object> storageData = getStorageData();
        File file = new File(filepath);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, storageData);
    }

    private Map<String, Object> getStorageData() {
        Map<String, Object> dataStorage = new ConcurrentHashMap<>();
        dataStorage.put("customerStorage", customerStorage);
        dataStorage.put("instructorStorage", instructorStorage);
        dataStorage.put("trainingStorage", trainingStorage);
        return dataStorage;
    }
}
