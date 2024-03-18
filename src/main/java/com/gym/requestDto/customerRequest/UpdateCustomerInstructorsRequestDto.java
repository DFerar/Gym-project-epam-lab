package com.gym.requestDto.customerRequest;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UpdateCustomerInstructorsRequestDto {
    private String customerUserName;
    @NotNull
    private List<String> instructorUserNames;
}