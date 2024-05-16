package com.gym.service.external;

import com.gym.dto.request.instructor.InstructorWorkloadRequest;
import com.gym.entity.InstructorEntity;
import com.gym.entity.TrainingEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExternalWorkloadCalculationService {
    private final DiscoveryClient discoveryClient;

    public void calculateWorkloadForCreation(InstructorEntity instructorEntity, TrainingEntity trainingEntity) {
        var workloadRequest = getInstructorWorkloadRequest(instructorEntity, trainingEntity);
        discoveryClient.getInstances("GymMicroserviceApplication").forEach(
            instance -> {
                String serviceUrl = instance.getUri().toString();
                var restClient = RestClient.create();
                var response = restClient
                    .post()
                    .uri(serviceUrl + "/workload/accept")
                    .body(workloadRequest)
                    .retrieve()
                    .toBodilessEntity();
                log.info("Instance: {}", instance.getUri());
                log.info("Response: {}", response);
            }
        );
    }

    private InstructorWorkloadRequest getInstructorWorkloadRequest(InstructorEntity instructorEntity,
                                                                   TrainingEntity trainingEntity) {
        InstructorWorkloadRequest workloadRequest = new InstructorWorkloadRequest();
        workloadRequest.setUsername(instructorEntity.getGymUserEntity().getUserName());
        workloadRequest.setFirstName(instructorEntity.getGymUserEntity().getFirstName());
        workloadRequest.setLastName(instructorEntity.getGymUserEntity().getLastName());
        workloadRequest.setIsActive(instructorEntity.getGymUserEntity().getIsActive());
        workloadRequest.setTrainingDate(trainingEntity.getTrainingDate());
        workloadRequest.setTrainingDuration(trainingEntity.getTrainingDuration());
        workloadRequest.setActionType("ADD");
        return workloadRequest;
    }
}
