package com.gym.responseDto.instructorResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerForInstructorResponseDto {
    private String userName;
    private String firstName;
    private String lastName;
}
