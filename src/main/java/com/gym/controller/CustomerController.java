package com.gym.controller;

import com.gym.dto.request.customer.CreateCustomerRequestDto;
import com.gym.dto.request.customer.UpdateCustomerInstructorsRequestDto;
import com.gym.dto.request.customer.UpdateCustomerProfileRequestDto;
import com.gym.dto.response.customer.CreateCustomerResponseDto;
import com.gym.dto.response.customer.GetCustomerProfileResponseDto;
import com.gym.dto.response.customer.InstructorForCustomerResponseDto;
import com.gym.dto.response.customer.UpdateCustomerProfileResponseDto;
import com.gym.entity.CustomerEntity;
import com.gym.entity.GymUserEntity;
import com.gym.entity.InstructorEntity;
import com.gym.mapper.CustomerMapper;
import com.gym.mapper.InstructorMapper;
import com.gym.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@Tag(name = "CustomerController", description = "API for Customer operations")
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;
    private final InstructorMapper instructorMapper;

    /**
     * This is @PostMapping("/create") or createCustomer method.
     * It creates a new customer entity and maps those details into
     * a response data transfer object to be returned to the client.
     *
     * @param customerDto The client request body containing details for customer creation.
     * @return {@code ResponseEntity<CreateCustomerResponseDto>} The created customer details mapped into a response data transfer object.
     */
    @PostMapping("/create")
    @Operation(summary = "Create a customer", description = "Creates a new customer and returns their data")
    public ResponseEntity<CreateCustomerResponseDto> createCustomer(
        @Valid @RequestBody CreateCustomerRequestDto customerDto) {
        GymUserEntity userEntity = customerMapper.mapCreateCustomerRequestDtoToUserEntity(customerDto);
        String password = userEntity.getPassword();
        CustomerEntity customerEntity = customerMapper.mapCreateCustomerRequestDtoToCustomerEntity(customerDto);
        CustomerEntity savedCustomer = customerService.createCustomer(customerEntity, userEntity);
        return new ResponseEntity<>(customerMapper.mapCustomerEntityToCreateResponseDto(savedCustomer
            .getGymUserEntity(), password), HttpStatus.CREATED);
    }

    /**
     * This is @GetMapping("/{username}") or getCustomerByUsername method.
     * It returns the details of a given customer entity identified by
     * the supplied username as a response data transfer object.
     *
     * @param username The unique identifier of the customer whose data is to be retrieved.
     * @return {@code ResponseEntity<GetCustomerProfileResponseDto>} The response data transfer object of the identified customer.
     */
    @GetMapping("/{username}")
    @Operation(summary = "Get customer data", description = "Returns the data of a customer by their username")
    public ResponseEntity<GetCustomerProfileResponseDto> getCustomerByUsername(@PathVariable String username) {
        CustomerEntity customerEntity = customerService.getCustomerByUserName(username);
        return new ResponseEntity<>(customerMapper.mapCustomerEntityToGetCustomerResponseDto(customerEntity),
            HttpStatus.OK);
    }

    /**
     * This is @PutMapping("/update") or updateCustomer method.
     * It updates a given customer entity's details with the supplied new information
     * and returns the updated details as a response data transfer object.
     *
     * @param newData New details to update into the customer profile.
     * @return {@code ResponseEntity<UpdateCustomerProfileResponseDto>} The updated customer details in a response data transfer object.
     */
    @PutMapping("/update")
    @Operation(summary = "Update customer data", description = "Updates a customer's data and returns the updated data")
    public ResponseEntity<UpdateCustomerProfileResponseDto> updateCustomer(
        @Valid @RequestBody UpdateCustomerProfileRequestDto newData) {
        GymUserEntity userEntityFromNewData = customerMapper.mapUpdateCustomerRequestDtoToUserEntity(newData);
        CustomerEntity customerEntityFromNewData = customerMapper.mapUpdateCustomerRequestDtoToCustomerEntity(newData);
        CustomerEntity updatedCustomer =
            customerService.updateCustomer(userEntityFromNewData, customerEntityFromNewData);
        return new ResponseEntity<>(customerMapper.mapCustomerEntityToUpdateCustomerResponseDto(updatedCustomer),
            HttpStatus.OK);
    }

    /**
     * This is @DeleteMapping("/{username}") or deleteCustomer method.
     * It deletes a given customer entity identified by the supplied username.
     *
     * @param username The unique identifier of the customer to be deleted.
     * @return {@code ResponseEntity<String>} An HTTP status indicating the success or failure of the deletion operation.
     */
    @DeleteMapping("/{username}")
    @Operation(summary = "Delete a customer", description = "Deletes a customer by their username")
    public ResponseEntity<String> deleteCustomer(@PathVariable String username) {
        customerService.deleteCustomerByUserName(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * This is @PatchMapping("/activate/{username}") or customerActivation method.
     * It changes the activation status of a given customer entity identified by the supplied username.
     *
     * @param username The unique identifier of the customer whose activation status is to be changed.
     * @return {@code ResponseEntity<String>} An HTTP status indicating the success or failure of the activation operation.
     */
    @PatchMapping("/activate/{username}")
    @Operation(summary = "Activate a customer", description = "Changes the activation status of a customer")
    public ResponseEntity<String> customerActivation(@PathVariable String username) {
        customerService.changeCustomersActivity(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * This is @PutMapping("/instructorList") or updateCustomerInstructors method.
     * It changes the list of instructors associated with a given customer
     * and returns a list of the updated instructors.
     *
     * @param requestDto The update request containing the customer and instructor details.
     * @return {@code ResponseEntity<List<InstructorForCustomerResponseDto>>} The updated instructor list as response data transfer objects.
     */
    @PutMapping("/instructorList")
    @Operation(summary = "Update customer instructors", description = "Updates the list of instructors for a customer")
    public ResponseEntity<List<InstructorForCustomerResponseDto>> updateCustomerInstructors(
        @Valid @RequestBody UpdateCustomerInstructorsRequestDto requestDto) {
        Set<InstructorEntity> instructorEntities = customerService.changeCustomerInstructors(
            requestDto.getCustomerUserName(), requestDto.getInstructorUserNames());
        return new ResponseEntity<>(instructorMapper.mapInstructorEntitiesToInstructorResponseDto(instructorEntities),
            HttpStatus.OK);
    }
}
