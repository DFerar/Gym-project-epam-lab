package com.gym.dto;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrainingDto {
    private Integer id;
    private Integer customerId;
    private Integer trainerId;
    private String trainingName;
    private Integer trainingTypeId;
    private Date trainingDate;
    private Integer trainingDuration;
}
