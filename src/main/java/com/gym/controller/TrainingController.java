package com.gym.controller;

import com.gym.dto.request.customer.GetCustomerTrainingListRequestDto;
import com.gym.dto.request.instructor.GetInstructorTrainingsRequestDto;
import com.gym.dto.request.training.CreateTrainingRequestDto;
import com.gym.dto.response.training.CustomerTrainingsResponseDto;
import com.gym.dto.response.training.InstructorTrainingsResponseDto;
import com.gym.entity.TrainingEntity;
import com.gym.mapper.TrainingMapper;
import com.gym.service.TrainingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/training")
@RequiredArgsConstructor
@Tag(name = "TrainingController", description = "API for Training operations")
public class TrainingController {
    private final TrainingService trainingService;
    private final TrainingMapper trainingMapper;

    /**
     * This is @GetMapping("/customer") or getCustomerTrainings method.
     * It returns a list of trainings associated with a particular customer.
     *
     * @param requestDto    The client request body with customer username and filters for retrieving the data.
     * @return {@code ResponseEntity<List<CustomerTrainingsResponseDto>>} A list of trainings associated with the specified customer.
     */
    @GetMapping("/customer")
    @Operation(summary = "Get customer trainings", description = "Returns a list of trainings for a specific customer")
    public ResponseEntity<List<CustomerTrainingsResponseDto>> getCustomerTrainings(
        @Valid @RequestBody GetCustomerTrainingListRequestDto requestDto) {
        List<TrainingEntity> trainingEntities = trainingService.getCustomerListOfTrainings(requestDto.getUserName(),
            requestDto.getFromDate(),
            requestDto.getToDate(),
            requestDto.getInstructorName(),
            requestDto.getTrainingType());
        return new ResponseEntity<>(trainingMapper.mapCustomerTrainingEntitiesToTrainingDtos(trainingEntities),
            HttpStatus.OK);
    }

    /**
     * This is @GetMapping("/instructor") or getInstructorTrainings method.
     * It returns the list of trainings conducted by a particular instructor.
     *
     * @param requestDto    The client request body with instructor username and other filtering data.
     * @return {@code ResponseEntity<List<InstructorTrainingsResponseDto>>} A list of trainings conducted by the specified instructor.
     */
    @GetMapping("/instructor")
    @Operation(summary = "Get instructor trainings",
        description = "Returns a list of trainings for a specific instructor")
    public ResponseEntity<List<InstructorTrainingsResponseDto>> getInstructorTrainings(
        @Valid @RequestBody GetInstructorTrainingsRequestDto requestDto) {
        List<TrainingEntity> trainingEntities = trainingService.getInstructorListOfTrainings(
            requestDto.getUserName(),
            requestDto.getFromDate(),
            requestDto.getToDate(),
            requestDto.getCustomerName());
        return new ResponseEntity<>(trainingMapper.mapInstructorTrainingEntitiesToTrainingDtos(trainingEntities),
            HttpStatus.OK);
    }

    /**
     * This is @PostMapping("/create") or createTraining method.
     * It creates a new training session and returns HTTP status 'CREATED'.
     *
     * @param trainingDto   The client request body with all necessary information to create a new training.
     * @return {@code ResponseEntity<String>} An HTTP status representing the result of the training creation.
     */
    @PostMapping("/create")
    @Operation(summary = "Create a training", description = "Creates a new training and returns CREATED status")
    public ResponseEntity<String> createTraining(@Valid @RequestBody CreateTrainingRequestDto trainingDto) {
        TrainingEntity trainingEntity = trainingMapper.mapCreateTrainingRequestDtoToTrainingEntity(trainingDto);
        trainingService.createTraining(trainingEntity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * This is @DeleteMapping("/delete/{id}") or deleteTraining method.
     * It deletes a training session by its id and returns HTTP status 'OK'.
     *
     * @param id    The unique identifier of the training session to be deleted.
     * @return {@code ResponseEntity<String>} An HTTP status representing the result of the training deletion.
     */
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a training by id", description = "Deletes a training session")
    public ResponseEntity<String> deleteTraining(@PathVariable Long id) {
        trainingService.deleteTraining(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
