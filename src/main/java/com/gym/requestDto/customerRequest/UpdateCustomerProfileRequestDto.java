package com.gym.requestDto.customerRequest;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class UpdateCustomerProfileRequestDto {
    @NotNull
    private String userName;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private Date dateOfBirth;
    private String address;
    @NotNull
    private Boolean isActive;
}
