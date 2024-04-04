package com.gym.responseDto.trainingResponse;

import com.gym.entity.TrainingType;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructorTrainingsResponseDto {
    private String trainingName;
    private LocalDate trainingDate;
    private TrainingType trainingType;
    private Integer trainingDuration;
    private String customerName;
}
