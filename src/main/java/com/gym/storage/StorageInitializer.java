package com.gym.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;


@Component
@RequiredArgsConstructor
public class StorageInitializer {

    private final Storage storage;

    @PostConstruct
    public void loadDataFromFiles() {
        String filePath = "/home/denis/IdeaProjects/Spring-core-task/datasource.json";
        loadEntityData(filePath);
    }

    private void loadEntityData(String filePath) {
        try {
            File file = new File(filePath);
            ObjectMapper objectMapper = new ObjectMapper();
            Storage entityMap = objectMapper.readValue(file, Storage.class);
            storage.setCustomerStorage(entityMap.getCustomerStorage());
            storage.setInstructorStorage(entityMap.getInstructorStorage());
            storage.setTrainingStorage(entityMap.getTrainingStorage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
