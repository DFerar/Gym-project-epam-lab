package com.gym.controller;

import com.gym.entity.TrainingTypeEntity;
import com.gym.mapper.TrainingMapper;
import com.gym.responseDto.trainingResponse.TrainingTypeResponseDto;
import com.gym.service.AuthenticationService;
import com.gym.service.TrainingTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/training-type")
@RequiredArgsConstructor
public class TrainingTypeController {
    private final TrainingTypeService trainingTypeService;
    private final AuthenticationService authenticationService;
    private final TrainingMapper trainingMapper;

    @GetMapping("/all")
    public ResponseEntity<List<TrainingTypeResponseDto>> getAllTrainingTypes(@RequestParam String loginUserName,
                                                                             @RequestParam String loginPassword) {
        if (!authenticationService.matchCredentials(loginUserName, loginPassword)) {
            throw new SecurityException("Unauthorized");
        }
        List<TrainingTypeEntity> trainingTypeEntities = trainingTypeService.getAllTrainingTypes();
        return new ResponseEntity<>(trainingMapper.mapTrainingTypeEntitiesToTrainingTypeResponseDto(trainingTypeEntities),
                HttpStatus.OK);
    }
}
