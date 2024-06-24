package com.gym.service.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym.dto.request.instructor.InstructorWorkloadRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class InstructorWorkloadProducer extends AbstractKafkaProducer<InstructorWorkloadRequest> {
    public InstructorWorkloadProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        super(kafkaTemplate, objectMapper);
    }

    public void sendMessage(String topic, InstructorWorkloadRequest request) {
        super.sendMessage(topic, request);
    }
}
