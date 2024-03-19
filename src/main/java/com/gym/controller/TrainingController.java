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
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/training")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingService trainingService;
    private final AuthenticationService authenticationService;
    private final TrainingMapper trainingMapper;

    @GetMapping("/customer")
    public ResponseEntity<List<CustomerTrainingsResponseDto>> getCustomerTrainings(
            @Valid @RequestBody GetCustomerTrainingListRequestDto requestDto,
            @RequestParam String loginUsername,
            @RequestParam String loginPassword) {
        if (!authenticationService.matchCustomerCredentials(loginUsername, loginPassword)) {
            throw new NoSuchElementException("Customer not found");
        }
        List<TrainingEntity> trainingEntities = trainingService.getCustomerListOfTrainings(requestDto.getUserName(),
                requestDto.getFromDate(), requestDto.getToDate(), requestDto.getInstructorName(),
                requestDto.getTrainingType());
        return new ResponseEntity<>(trainingMapper.mapCustomerTrainingEntitiesToTrainingDtos(trainingEntities),
                HttpStatus.OK);
    }

    @GetMapping("/instructor")
    public ResponseEntity<List<InstructorTrainingsResponseDto>> getInstructorTrainings(
            @Valid @RequestBody GetInstructorTrainingsRequestDto requestDto,
            @RequestParam String loginUsername,
            @RequestParam String loginPassword) {
        if (!authenticationService.matchInstructorCredentials(loginUsername, loginPassword)) {
            throw new NoSuchElementException("Instructor not found");
        }
        List<TrainingEntity> trainingEntities = trainingService.getInstructorListOfTrainings(requestDto.getUserName(),
                requestDto.getFromDate(), requestDto.getToDate(), requestDto.getCustomerName());
        return new ResponseEntity<>(trainingMapper.mapInstructorTrainingEntitiesToTrainingDtos(trainingEntities),
                HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createTraining(@Valid @RequestBody CreateTrainingRequestDto trainingDto,
                               @RequestParam String loginUsername,
                               @RequestParam String loginPassword) {
        if (!authenticationService.matchCredentials(loginUsername, loginPassword)) {
            throw new NoSuchElementException("User not found");
        }
        TrainingEntity trainingEntity = trainingMapper.mapCreateTrainingRequestDtoToTrainingEntity(trainingDto);
        trainingService.createTraining(trainingEntity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
