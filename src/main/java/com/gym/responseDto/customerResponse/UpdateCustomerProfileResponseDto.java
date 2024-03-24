package com.gym.responseDto.customerResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerProfileResponseDto {
    private String userName;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String address;
    private Boolean isActive;
    private List<InstructorForCustomerResponseDto> instructors;
}
