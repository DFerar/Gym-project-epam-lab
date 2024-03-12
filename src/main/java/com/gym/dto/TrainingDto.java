package com.gym.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class TrainingDto {
    private Integer id;
    private Integer customerId;
    private Integer instructorId;
    private String trainingName;
    private Integer trainingTypeId;
    private Date trainingDate;
    private Integer trainingDuration;
}
