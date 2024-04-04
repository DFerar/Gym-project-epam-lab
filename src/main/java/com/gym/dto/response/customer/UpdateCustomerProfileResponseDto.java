package com.gym.dto.response.customer;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerProfileResponseDto {
    private String userName;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;
    private Boolean isActive;
    private List<InstructorForCustomerResponseDto> instructors;
}
