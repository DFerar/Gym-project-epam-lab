package com.gym.responseDto.trainingResponse;

import com.gym.entity.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class CustomerTrainingsResponseDto {
    private String trainingName;
    private Date trainingDate;
    private TrainingType trainingType;
    private Integer trainingDuration;
    private String instructorName;
}
