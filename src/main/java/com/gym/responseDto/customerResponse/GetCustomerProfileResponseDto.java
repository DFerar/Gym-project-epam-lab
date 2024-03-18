package com.gym.responseDto.customerResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class GetCustomerProfileResponseDto {
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String address;
    private Boolean isActive;
    private List<InstructorForCustomerResponseDto> instructors;
}
