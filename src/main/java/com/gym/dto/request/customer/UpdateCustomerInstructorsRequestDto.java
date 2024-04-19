package com.gym.dto.request.customer;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerInstructorsRequestDto {
    private String customerUserName;
    @NotNull(message = "Should not be empty")
    private List<String> instructorUserNames;
}