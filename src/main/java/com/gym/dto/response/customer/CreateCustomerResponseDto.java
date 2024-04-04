package com.gym.dto.response.customer;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerResponseDto {
    private String userName;
    private String password;
}
