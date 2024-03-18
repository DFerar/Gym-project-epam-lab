package com.gym.responseDto.instructorResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateInstructorResponseDto {
    private String userName;
    private String password;
}