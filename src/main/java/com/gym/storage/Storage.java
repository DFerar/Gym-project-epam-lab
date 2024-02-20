package com.gym.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym.customer.Customer;
import com.gym.training.Training;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Getter
@Setter
public class Storage {
    private  Map<Integer, Customer> customerStorage = new ConcurrentHashMap<>();
    private  Map<Integer, com.gym.instructor.Instructor> instructorStorage = new ConcurrentHashMap<>();
    private  Map<Integer, Training> trainingStorage = new ConcurrentHashMap<>();

    public Map<String, Object> getStorageData() {
        Map<String, Object> dataStorage = new ConcurrentHashMap<>();
        dataStorage.put("customerStorage", customerStorage);
        dataStorage.put("instructorStorage", instructorStorage);
        dataStorage.put("trainingStorage", trainingStorage);
        return dataStorage;
    }

    public void updateDatasource(Map<String, Object> datastorage) throws IOException {
        File file  = new File("/home/denis/IdeaProjects/Spring-core-task/datasource.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, datastorage);
    }

    public void clearStorage(Map<String, Object> datastorage) {
        datastorage.clear();
    }
}
