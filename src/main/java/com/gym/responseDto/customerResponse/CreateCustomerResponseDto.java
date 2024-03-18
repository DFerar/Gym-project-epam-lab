package com.gym.responseDto.customerResponse;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCustomerResponseDto {
    private String userName;
    private String password;
}
