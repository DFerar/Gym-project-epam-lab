package com.gym.service.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym.dto.request.instructor.InstructorWorkloadRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendMessage(String topic, String request) {
        kafkaTemplate.send(topic, request);
    }

    @SneakyThrows
    public String convertObjectToString(InstructorWorkloadRequest request) {
        return objectMapper.writeValueAsString(request);
    }
}
