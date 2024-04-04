package com.gym.controller;

import com.gym.entity.TrainingTypeEntity;
import com.gym.mapper.TrainingMapper;
import com.gym.responseDto.trainingResponse.TrainingTypeResponseDto;
import com.gym.service.AuthenticationService;
import com.gym.service.TrainingTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/training-type")
@RequiredArgsConstructor
@Tag(name = "TrainingTypeController", description = "API for Training Type operations")
public class TrainingTypeController {
    private final TrainingTypeService trainingTypeService;
    private final AuthenticationService authenticationService;
    private final TrainingMapper trainingMapper;

    @GetMapping("/all")
    @Operation(summary = "Get all training types", description = "Returns a list of all training types")
    public ResponseEntity<List<TrainingTypeResponseDto>> getAllTrainingTypes(@RequestParam String loginUserName,
                                                                             @RequestParam String loginPassword) {
        authenticationService.matchCredentials(loginUserName, loginPassword);
        List<TrainingTypeEntity> trainingTypeEntities = trainingTypeService.getAllTrainingTypes();
        return new ResponseEntity<>(
            trainingMapper.mapTrainingTypeEntitiesToTrainingTypeResponseDto(trainingTypeEntities),
            HttpStatus.OK);
    }
}
