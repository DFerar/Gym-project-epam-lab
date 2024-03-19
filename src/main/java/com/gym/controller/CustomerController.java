package com.gym.controller;

import com.gym.entity.CustomerEntity;
import com.gym.entity.GymUserEntity;
import com.gym.entity.InstructorEntity;
import com.gym.mapper.CustomerMapper;
import com.gym.mapper.InstructorMapper;
import com.gym.requestDto.customerRequest.CreateCustomerRequestDto;
import com.gym.requestDto.customerRequest.UpdateCustomerInstructorsRequestDto;
import com.gym.requestDto.customerRequest.UpdateCustomerProfileRequestDto;
import com.gym.responseDto.customerResponse.CreateCustomerResponseDto;
import com.gym.responseDto.customerResponse.GetCustomerProfileResponseDto;
import com.gym.responseDto.customerResponse.InstructorForCustomerResponseDto;
import com.gym.responseDto.customerResponse.UpdateCustomerProfileResponseDto;
import com.gym.service.AuthenticationService;
import com.gym.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;
    private final InstructorMapper instructorMapper;
    private final AuthenticationService authenticationService;

    @PostMapping("/create")
    public ResponseEntity<CreateCustomerResponseDto> createCustomer(
            @Valid @RequestBody CreateCustomerRequestDto customerDto) {
        GymUserEntity userEntity = customerMapper.mapCreateCustomerRequestDtoToUserEntity(customerDto);
        CustomerEntity customerEntity = customerMapper.mapCreateCustomerRequestDtoToCustomerEntity(customerDto);
        CustomerEntity savedCustomer = customerService.createCustomer(customerEntity, userEntity);
        return new ResponseEntity<>(customerMapper.mapCustomerEntityToCreateResponseDto(savedCustomer
                .getGymUserEntity()), HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<GetCustomerProfileResponseDto> getCustomerByUsername(@PathVariable String username,
                                                                               @RequestParam String loginUserName,
                                                                               @RequestParam String loginPassword) {
        if (!authenticationService.matchCustomerCredentials(loginUserName, loginPassword)) {
            throw new SecurityException("Unauthorized");
        }
        CustomerEntity customerEntity = customerService.getCustomerByUserName(username);
        return new ResponseEntity<>(customerMapper.mapCustomerEntityToGetCustomerResponseDto(customerEntity),
                HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<UpdateCustomerProfileResponseDto> updateCustomer(
            @Valid @RequestBody UpdateCustomerProfileRequestDto newData,
            @RequestParam String loginUserName,
            @RequestParam String loginPassword) {
        if (!authenticationService.matchCustomerCredentials(loginUserName, loginPassword)) {
            throw new SecurityException("Unauthorized");
        }
        GymUserEntity userEntityFromNewData = customerMapper.mapUpdateCustomerRequestDtoToUserEntity(newData);
        CustomerEntity customerEntityFromNewData = customerMapper.mapUpdateCustomerRequestDtoToCustomerEntity(newData);
        CustomerEntity updatedCustomer = customerService.updateCustomer(userEntityFromNewData, customerEntityFromNewData);
        return new ResponseEntity<>(customerMapper.mapCustomerEntityToUpdateCustomerResponseDto(updatedCustomer),
                HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String username,
                                                 @RequestParam String loginUserName,
                                                 @RequestParam String loginPassword) {
        if (!authenticationService.matchCustomerCredentials(loginUserName, loginPassword)) {
            throw new SecurityException("Unauthorized");
        }
        customerService.deleteCustomerByUserName(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/activate/{username}")
    public ResponseEntity<String> customerActivation(@PathVariable String username,
                                                     @RequestParam Boolean isActive,
                                                     @RequestParam String loginUserName,
                                                     @RequestParam String loginPassword) {
        if (!authenticationService.matchCustomerCredentials(loginUserName, loginPassword)) {
            throw new SecurityException("Unauthorized");
        }
        customerService.changeCustomersActivity(username, isActive);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/instructorList")
    public ResponseEntity<List<InstructorForCustomerResponseDto>> updateCustomerInstructors(
            @Valid @RequestBody UpdateCustomerInstructorsRequestDto requestDto,
            @RequestParam String loginUserName,
            @RequestParam String loginPassword) {
        if (!authenticationService.matchCustomerCredentials(loginUserName, loginPassword)) {
            throw new SecurityException("Unauthorized");
        }
        Set<InstructorEntity> instructorEntities = customerService.changeCustomerInstructors(
                requestDto.getCustomerUserName(), requestDto.getInstructorUserNames());
        return new ResponseEntity<>(instructorMapper.mapInstructorEntitiesToInstructorResponseDto(instructorEntities),
                HttpStatus.OK);
    }
}
