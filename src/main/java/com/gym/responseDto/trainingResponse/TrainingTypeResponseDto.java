package com.gym.responseDto.trainingResponse;

import com.gym.entity.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrainingTypeResponseDto {
    private Long trainingTypeId;
    private TrainingType trainingTypeName;
}
