package com.gym.controller;

import com.gym.dto.response.training.TrainingTypeResponseDto;
import com.gym.entity.TrainingTypeEntity;
import com.gym.mapper.TrainingMapper;
import com.gym.service.TrainingTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/training-type")
@RequiredArgsConstructor
@Tag(name = "TrainingTypeController", description = "API for Training Type operations")
public class TrainingTypeController {
    private final TrainingTypeService trainingTypeService;
    private final TrainingMapper trainingMapper;

    /**
     * This is @GetMapping("/all") or getAllTrainingTypes method.
     * It retrieves a list of all training types available in the system.
     *
     * @return {@code ResponseEntity<List<TrainingTypeResponseDto>>} A list of all training types in the system.
     */
    @GetMapping("/all")
    @Operation(summary = "Get all training types", description = "Returns a list of all training types")
    public ResponseEntity<List<TrainingTypeResponseDto>> getAllTrainingTypes() {
        List<TrainingTypeEntity> trainingTypeEntities = trainingTypeService.getAllTrainingTypes();
        return new ResponseEntity<>(
            trainingMapper.mapTrainingTypeEntitiesToTrainingTypeResponseDto(trainingTypeEntities),
            HttpStatus.OK);
    }
}
