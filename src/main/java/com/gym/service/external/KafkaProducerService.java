package com.gym.service.external;

import com.gym.dto.request.instructor.InstructorWorkloadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, InstructorWorkloadRequest> kafkaTemplate;

    public void sendMessage(String topic, InstructorWorkloadRequest request) {
        kafkaTemplate.send(topic, request); //TODO: send string instead of object
    }
}
