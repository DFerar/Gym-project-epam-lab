package com.gym.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingEntity {
    private Integer trainingId;
    private Integer customerId;
    private Integer instructorId;
    private String trainingName;
    private TrainingType trainingType;
    private String trainingDate;
    private String trainingDuration;
}
