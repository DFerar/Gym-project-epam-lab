package com.gym.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class TrainingDto {
    private Long id;
    private Long customerId;
    private Long instructorId;
    private String trainingName;
    private Long trainingTypeId;
    private Date trainingDate;
    private Integer trainingDuration;
}
