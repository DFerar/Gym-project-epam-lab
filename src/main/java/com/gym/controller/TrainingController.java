package com.gym.controller;

import com.gym.entity.TrainingEntity;
import com.gym.mapper.TrainingMapper;
import com.gym.requestDto.customerRequest.GetCustomerTrainingListRequestDto;
import com.gym.requestDto.instructorRequest.GetInstructorTrainingsRequestDto;
import com.gym.requestDto.trainingRequest.CreateTrainingRequestDto;
import com.gym.responseDto.trainingResponse.CustomerTrainingsResponseDto;
import com.gym.responseDto.trainingResponse.InstructorTrainingsResponseDto;
import com.gym.service.AuthenticationService;
import com.gym.service.TrainingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/training")
@RequiredArgsConstructor
//@Tag(name = "TrainingController", description = "API for Training operations")
public class TrainingController {
    private final TrainingService trainingService;
    private final AuthenticationService authenticationService;
    private final TrainingMapper trainingMapper;

    @GetMapping("/customer")
    //@Operation(summary = "Get customer trainings", description = "Returns a list of trainings for a specific customer")
    public ResponseEntity<List<CustomerTrainingsResponseDto>> getCustomerTrainings(
            @Valid @RequestBody/*(description = "Customer training list request data", required = true,
                    content = @Content(schema = @Schema(implementation = GetCustomerTrainingListRequestDto.class)))*/
                    GetCustomerTrainingListRequestDto requestDto,
            @RequestParam String loginUsername,
            @RequestParam String loginPassword) {
        authenticationService.matchCustomerCredentials(loginUsername, loginPassword);
        List<TrainingEntity> trainingEntities = trainingService.getCustomerListOfTrainings(requestDto.getUserName(),
                trainingMapper.mapStringDateToObject(requestDto.getFromDate()),
                trainingMapper.mapStringDateToObject(requestDto.getToDate()),
                requestDto.getInstructorName(),
                requestDto.getTrainingType());
        return new ResponseEntity<>(trainingMapper.mapCustomerTrainingEntitiesToTrainingDtos(trainingEntities),
                HttpStatus.OK);
    }

    @GetMapping("/instructor")
    /*@Operation(summary = "Get instructor trainings",
            description = "Returns a list of trainings for a specific instructor")*/
    public ResponseEntity<List<InstructorTrainingsResponseDto>> getInstructorTrainings(
            @Valid @RequestBody/*(description = "Instructor trainings request data", required = true,
                    content = @Content(schema = @Schema(implementation = GetInstructorTrainingsRequestDto.class)))*/
            GetInstructorTrainingsRequestDto requestDto,
            @RequestParam String loginUsername,
            @RequestParam String loginPassword) {
        authenticationService.matchInstructorCredentials(loginUsername, loginPassword);
        List<TrainingEntity> trainingEntities = trainingService.getInstructorListOfTrainings(
                requestDto.getUserName(),
                trainingMapper.mapStringDateToObject(requestDto.getFromDate()),
                trainingMapper.mapStringDateToObject(requestDto.getToDate()),
                requestDto.getCustomerName());
        return new ResponseEntity<>(trainingMapper.mapInstructorTrainingEntitiesToTrainingDtos(trainingEntities),
                HttpStatus.OK);
    }

    @PostMapping("/create")
    //@Operation(summary = "Create a training", description = "Creates a new training and returns CREATED status")
    public ResponseEntity<String> createTraining(@Valid @RequestBody/*(description = "New training data", required = true,
            content = @Content(schema = @Schema(implementation = CreateTrainingRequestDto.class)))*/
                                                 CreateTrainingRequestDto trainingDto,
                                                 @RequestParam String loginUsername,
                                                 @RequestParam String loginPassword) {
        authenticationService.matchCredentials(loginUsername, loginPassword);
        TrainingEntity trainingEntity = trainingMapper.mapCreateTrainingRequestDtoToTrainingEntity(trainingDto);
        trainingService.createTraining(trainingEntity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
