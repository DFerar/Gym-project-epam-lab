package com.gym.responseDto.trainingResponse;

import com.gym.entity.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructorTrainingsResponseDto {
    private String trainingName;
    private String trainingDate;
    private TrainingType trainingType;
    private Integer trainingDuration;
    private String customerName;
}
