package com.gym.controller;

import com.gym.responseDto.trainingResponse.TrainingTypeResponseDto;
import com.gym.service.TrainingTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/training-type")
@RequiredArgsConstructor
public class TrainingTypeController {
    private final TrainingTypeService trainingTypeService;

    @GetMapping("/all")
    public List<TrainingTypeResponseDto> getAllTrainingTypes() {
        return trainingTypeService.getAllTrainingTypes();
    }
}