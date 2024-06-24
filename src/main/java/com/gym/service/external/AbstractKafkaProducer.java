package com.gym.service.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public abstract class AbstractKafkaProducer<T> {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public void sendMessage(String topic, T request) {
        String message = objectMapper.writeValueAsString(request);
        kafkaTemplate.send(topic, message);
    }
}
