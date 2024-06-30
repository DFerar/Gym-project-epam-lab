package com.gym.service.external;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.gym.entity.GymUserEntity;
import com.gym.entity.InstructorEntity;
import com.gym.entity.TrainingEntity;
import java.time.LocalDate;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class ExternalWorkloadCalculationServiceTest {

    @Mock
    private InstructorWorkloadProducer gymMicroserviceClient;

    @InjectMocks
    private ExternalWorkloadCalculationService externalWorkloadCalculationService;

    @Test
    void shouldCalculateWorkloadForCreation() {
        //Given
        InstructorEntity instructorEntity = new InstructorEntity();
        final TrainingEntity trainingEntity = new TrainingEntity();
        GymUserEntity gymUserEntity = new GymUserEntity();
        gymUserEntity.setUserName(RandomStringUtils.randomAlphabetic(10));
        gymUserEntity.setFirstName(RandomStringUtils.randomAlphabetic(10));
        gymUserEntity.setLastName(RandomStringUtils.randomAlphabetic(10));
        gymUserEntity.setIsActive(true);
        instructorEntity.setGymUserEntity(gymUserEntity);
        trainingEntity.setTrainingDate(LocalDate.now());
        trainingEntity.setTrainingDuration(5);
        //When
        externalWorkloadCalculationService.calculateWorkloadForCreation(instructorEntity, trainingEntity);
        //Then
        verify(gymMicroserviceClient, times(1)).sendMessage(any(), any());
    }

    @Test
    void shouldCalculateWorkloadForDeleting() {
        //Given
        InstructorEntity instructorEntity = new InstructorEntity();
        final TrainingEntity trainingEntity = new TrainingEntity();
        GymUserEntity gymUserEntity = new GymUserEntity();
        gymUserEntity.setUserName(RandomStringUtils.randomAlphabetic(10));
        gymUserEntity.setFirstName(RandomStringUtils.randomAlphabetic(10));
        gymUserEntity.setLastName(RandomStringUtils.randomAlphabetic(10));
        gymUserEntity.setIsActive(true);
        instructorEntity.setGymUserEntity(gymUserEntity);
        trainingEntity.setTrainingDate(LocalDate.now());
        trainingEntity.setTrainingDuration(5);
        //When
        externalWorkloadCalculationService.calculateWorkloadForDeletion(instructorEntity, trainingEntity);
        //Then
        verify(gymMicroserviceClient, times(1)).sendMessage(any(), any());
    }
}

