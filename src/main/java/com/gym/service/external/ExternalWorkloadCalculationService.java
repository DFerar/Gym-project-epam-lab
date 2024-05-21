package com.gym.service.external;

import com.gym.dto.request.instructor.InstructorWorkloadRequest;
import com.gym.entity.InstructorEntity;
import com.gym.entity.TrainingEntity;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExternalWorkloadCalculationService {
    private final GymMicroserviceClient gymMicroserviceClient;

    /**
     * This method calculates the workload for creation of a new training session.
     * It uses a circuit breaker and retry mechanism to handle failures when calling the external service.
     *
     * @param instructorEntity The instructor entity for which the workload is being calculated.
     * @param trainingEntity   The training entity for which the workload is being calculated.
     */
    public void calculateWorkloadForCreation(InstructorEntity instructorEntity, TrainingEntity trainingEntity) {
        var workloadRequest = getInstructorWorkloadRequest(instructorEntity, trainingEntity);
        workloadRequest.setActionType("ADD");
        gymMicroserviceClient.acceptWorkload(workloadRequest);
    }

    /**
     * This method calculates the workload for deletion of a training session.
     * It calls the external service to perform the calculation.
     *
     * @param instructorEntity The instructor entity for which the workload is being calculated.
     * @param trainingEntity   The training entity for which the workload is being calculated.
     */
    @CircuitBreaker(name = "cb")
    @Retry(name = "retry", fallbackMethod = "fallback")
    public void calculateWorkloadForDeletion(InstructorEntity instructorEntity, TrainingEntity trainingEntity) {
        var workloadRequest = getInstructorWorkloadRequest(instructorEntity, trainingEntity);
        workloadRequest.setActionType("DELETE");
        gymMicroserviceClient.acceptWorkload(workloadRequest);
    }

    /**
     * This method creates a new InstructorWorkloadRequest object from the given InstructorEntity and TrainingEntity.
     *
     * @param instructorEntity The instructor entity for which the workload is being calculated.
     * @param trainingEntity   The training entity for which the workload is being calculated.
     * @return The created InstructorWorkloadRequest object.
     */
    private InstructorWorkloadRequest getInstructorWorkloadRequest(InstructorEntity instructorEntity,
                                                                   TrainingEntity trainingEntity) {
        InstructorWorkloadRequest workloadRequest = new InstructorWorkloadRequest();
        workloadRequest.setUsername(instructorEntity.getGymUserEntity().getUserName());
        workloadRequest.setFirstName(instructorEntity.getGymUserEntity().getFirstName());
        workloadRequest.setLastName(instructorEntity.getGymUserEntity().getLastName());
        workloadRequest.setIsActive(instructorEntity.getGymUserEntity().getIsActive());
        workloadRequest.setTrainingDate(trainingEntity.getTrainingDate());
        workloadRequest.setTrainingDuration(trainingEntity.getTrainingDuration());
        return workloadRequest;
    }
}
