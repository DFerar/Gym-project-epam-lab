package com.gym.dto.request.instructor;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstructorWorkloadRequest {
    private String username;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private LocalDate trainingDate;
    private Integer trainingDuration;
    private ActionType actionType;
}
