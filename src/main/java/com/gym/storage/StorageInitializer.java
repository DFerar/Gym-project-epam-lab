package com.gym.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;


@Component
@RequiredArgsConstructor
public class StorageInitializer {

    private final Storage storage;
    @Value("${datasource.path}")
    private String filepath;

    @PostConstruct
    @SneakyThrows
    public void loadEntityData() {
        File file = new File(filepath);
        ObjectMapper objectMapper = new ObjectMapper();
        Storage entityMap = objectMapper.readValue(file, Storage.class);
        storage.setCustomerStorage(entityMap.getCustomerStorage());
        storage.setInstructorStorage(entityMap.getInstructorStorage());
        storage.setTrainingStorage(entityMap.getTrainingStorage());
    }
}
