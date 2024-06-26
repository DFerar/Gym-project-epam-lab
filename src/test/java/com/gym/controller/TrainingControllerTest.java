package com.gym.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.gym.dto.request.customer.GetCustomerTrainingListRequestDto;
import com.gym.dto.request.instructor.GetInstructorTrainingsRequestDto;
import com.gym.dto.request.training.CreateTrainingRequestDto;
import com.gym.dto.response.training.CustomerTrainingsResponseDto;
import com.gym.dto.response.training.InstructorTrainingsResponseDto;
import com.gym.mapper.TrainingMapper;
import com.gym.service.TrainingService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class TrainingControllerTest {
    @Mock
    private TrainingService trainingService;

    @Mock
    private TrainingMapper trainingMapper;

    @InjectMocks
    private TrainingController trainingController;

    @Test
    public void shouldGetCustomerTrainings() {
        // Given
        GetCustomerTrainingListRequestDto requestDto = new GetCustomerTrainingListRequestDto();

        when(trainingMapper.mapCustomerTrainingEntitiesToTrainingDtos(anyList())).thenReturn(Collections.emptyList());

        // When
        ResponseEntity<List<CustomerTrainingsResponseDto>> response =
            trainingController.getCustomerTrainings(requestDto);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void shouldGetInstructorTrainings() {
        // Given
        GetInstructorTrainingsRequestDto requestDto = new GetInstructorTrainingsRequestDto();

        when(trainingService.getInstructorListOfTrainings(eq(requestDto.getUserName()), eq(null), eq(null),
            eq(requestDto.getCustomerName()))).thenReturn(Collections.emptyList());
        when(trainingMapper.mapInstructorTrainingEntitiesToTrainingDtos(anyList())).thenReturn(Collections.emptyList());

        // When
        ResponseEntity<List<InstructorTrainingsResponseDto>> response =
            trainingController.getInstructorTrainings(requestDto);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void shouldCreateTraining() {
        // Given
        CreateTrainingRequestDto requestDto = new CreateTrainingRequestDto();

        // When
        ResponseEntity<String> response = trainingController.createTraining(requestDto);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}
