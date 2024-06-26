package com.gym.dto.response.training;

import com.gym.entity.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingTypeResponseDto {
    private Long trainingTypeId;
    private TrainingType trainingTypeName;
}
