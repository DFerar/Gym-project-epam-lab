package com.gym.requestDto.instructorRequest;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class GetInstructorTrainingsRequestDto {
    @NotNull
    private String userName;
    private Date fromDate;
    private Date toDate;
    private String customerName;
}
