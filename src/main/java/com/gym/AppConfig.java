package com.gym;

import com.gym.storage.Storage;
import com.gym.storage.StorageInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@ComponentScan
@PropertySource("classpath:application.yml")
public class AppConfig {
    @Bean
    public Storage storage() {
        return new Storage();
    }

    @Bean
    public StorageInitializer storageInitializer() {
        return new StorageInitializer(storage());
    }
}
