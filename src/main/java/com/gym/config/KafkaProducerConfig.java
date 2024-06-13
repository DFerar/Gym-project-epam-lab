package com.gym.config;

import com.gym.dto.request.instructor.InstructorWorkloadRequest;
import com.gym.properties.KafkaProperties;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(KafkaProperties.class)
@EnableKafka
public class KafkaProducerConfig {
    private final KafkaProperties kafkaProperties;

    @Bean
    public Map<String, Object> producerFactoryConfig() {
        var configProps = new HashMap<String, Object>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return configProps;
    }

    @Bean
    public ProducerFactory<String, InstructorWorkloadRequest> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerFactoryConfig());
    }

    @Bean
    public KafkaTemplate<String, InstructorWorkloadRequest> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
