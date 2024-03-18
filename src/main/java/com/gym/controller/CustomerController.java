package com.gym.controller;

import com.gym.entity.CustomerEntity;
import com.gym.entity.GymUserEntity;
import com.gym.mapper.CustomerMapper;
import com.gym.requestDto.customerRequest.CreateCustomerRequestDto;
import com.gym.requestDto.customerRequest.UpdateCustomerProfileRequestDto;
import com.gym.responseDto.customerResponse.CreateCustomerResponseDto;
import com.gym.responseDto.customerResponse.GetCustomerProfileResponseDto;
import com.gym.responseDto.customerResponse.UpdateCustomerProfileResponseDto;
import com.gym.service.AuthenticationService;
import com.gym.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;
    private final AuthenticationService authenticationService;

    @PostMapping("/create")
    public CreateCustomerResponseDto createCustomer(@RequestBody CreateCustomerRequestDto customerDto) {
        GymUserEntity userEntity = customerMapper.mapCreateCustomerRequestDtoToUserEntity(customerDto);
        CustomerEntity customerEntity = customerMapper.mapCreateCustomerRequestDtoToCustomerEntity(customerDto);
        CustomerEntity savedCustomer = customerService.createCustomer(customerEntity, userEntity);
        return customerMapper.mapCustomerEntityToCreateResponseDto(savedCustomer
                .getGymUserEntity());
    }

    @GetMapping("/{username}")
    public GetCustomerProfileResponseDto getCustomerByUsername(@PathVariable String username,
                                                               @RequestParam String loginUserName,
                                                               @RequestParam String loginPassword) {
        if (!authenticationService.matchCustomerCredentials(loginUserName, loginPassword)) {
            throw new SecurityException("Unauthorized");
        }
        CustomerEntity customerEntity = customerService.getCustomerByUserName(username);
        return customerMapper.mapCustomerEntityToGetCustomerResponseDto(customerEntity);
    }

    @PutMapping("/update")
    public UpdateCustomerProfileResponseDto updateCustomer(@RequestBody UpdateCustomerProfileRequestDto newData,
                                                           @RequestParam String loginUserName,
                                                           @RequestParam String loginPassword) {
        if (!authenticationService.matchCustomerCredentials(loginUserName, loginPassword)) {
            throw new SecurityException("Unauthorized");
        }
        GymUserEntity userEntityFromNewData = customerMapper.mapUpdateCustomerRequestDtoToUserEntity(newData);
        CustomerEntity customerEntityFromNewData = customerMapper.mapUpdateCustomerRequestDtoToCustomerEntity(newData);
        CustomerEntity updatedCustomer = customerService.updateCustomer(userEntityFromNewData, customerEntityFromNewData);
        return customerMapper.mapCustomerEntityToUpdateCustomerResponseDto(updatedCustomer);
    }

    @DeleteMapping("/{username}")
    public void deleteCustomer(@PathVariable String username,
                               @RequestParam String loginUserName,
                               @RequestParam String loginPassword) {
        if (!authenticationService.matchCustomerCredentials(loginUserName, loginPassword)) {
            throw new SecurityException("Unauthorized");
        }
        customerService.deleteCustomerByUserName(username);
    }
}
