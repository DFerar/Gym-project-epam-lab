//package com.gym.config;
//
//import com.gym.properties.KafkaProperties;
//import lombok.RequiredArgsConstructor;
//import org.apache.kafka.clients.admin.NewTopic;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.TopicBuilder;
//
//@Configuration
//@RequiredArgsConstructor
//public class KafkaTopicConfig {
//    private final KafkaProperties kafkaProperties;
//    private static final String TOPIC = "gym-topic";
//
//    @Bean
//    public NewTopic topic() {
//        return TopicBuilder.name(TOPIC)
//                .partitions(1)
//                .replicas(1)
//                .build();
//    }
//}
