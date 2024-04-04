package com.gym.mapper;

import static com.gym.utils.Utils.generatePassword;

import com.gym.entity.CustomerEntity;
import com.gym.entity.GymUserEntity;
import com.gym.entity.InstructorEntity;
import com.gym.requestDto.customerRequest.CreateCustomerRequestDto;
import com.gym.requestDto.customerRequest.UpdateCustomerProfileRequestDto;
import com.gym.responseDto.customerResponse.CreateCustomerResponseDto;
import com.gym.responseDto.customerResponse.GetCustomerProfileResponseDto;
import com.gym.responseDto.customerResponse.InstructorForCustomerResponseDto;
import com.gym.responseDto.customerResponse.UpdateCustomerProfileResponseDto;
import com.gym.service.GymUserService;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerMapper {
    private final GymUserService gymUserService;

    public GymUserEntity mapCreateCustomerRequestDtoToUserEntity(CreateCustomerRequestDto customer) {
        GymUserEntity gymUserEntity = new GymUserEntity();
        gymUserEntity.setFirstName(customer.getFirstName());
        gymUserEntity.setLastName(customer.getLastName());
        gymUserEntity.setPassword(generatePassword());
        gymUserEntity.setUserName(gymUserService.generateUniqueUserName(customer.getFirstName(),
                customer.getLastName()));
        gymUserEntity.setIsActive(true);
        return gymUserEntity;
    }

    public CustomerEntity mapCreateCustomerRequestDtoToCustomerEntity(CreateCustomerRequestDto customerDto) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setAddress(customerDto.getAddress());
        customerEntity.setDateOfBirth(customerDto.getDateOfBirth());
        return customerEntity;
    }

    public CreateCustomerResponseDto mapCustomerEntityToCreateResponseDto(GymUserEntity savedUser) {
        return new CreateCustomerResponseDto(savedUser.getUserName(),
                savedUser.getPassword());
    }

    public GetCustomerProfileResponseDto mapCustomerEntityToGetCustomerResponseDto(CustomerEntity customerEntity) {
        return new GetCustomerProfileResponseDto(
                customerEntity.getGymUserEntity().getFirstName(), customerEntity.getGymUserEntity().getLastName(),
                customerEntity.getDateOfBirth(), customerEntity.getAddress(), customerEntity.getGymUserEntity().getIsActive(),
                mapInstructorEntitiesToInstructorDtos(customerEntity.getInstructors())
        );
    }

    public GymUserEntity mapUpdateCustomerRequestDtoToUserEntity(UpdateCustomerProfileRequestDto newData) {
        GymUserEntity gymUserEntity = new GymUserEntity();
        gymUserEntity.setUserName(newData.getUserName());
        gymUserEntity.setFirstName(newData.getFirstName());
        gymUserEntity.setLastName(newData.getLastName());
        gymUserEntity.setIsActive(newData.getIsActive());
        return gymUserEntity;
    }

    public CustomerEntity mapUpdateCustomerRequestDtoToCustomerEntity(UpdateCustomerProfileRequestDto newData) {
        GymUserEntity gymUserEntity = new GymUserEntity();
        gymUserEntity.setUserName(newData.getUserName());
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setDateOfBirth(newData.getDateOfBirth());
        customerEntity.setAddress(newData.getAddress());
        return customerEntity;
    }

    private List<InstructorForCustomerResponseDto> mapInstructorEntitiesToInstructorDtos(Set<InstructorEntity> instructors) {
        return instructors.stream()
                .map(instructorEntity -> new InstructorForCustomerResponseDto(
                        instructorEntity.getGymUserEntity().getUserName(),
                        instructorEntity.getGymUserEntity().getFirstName(),
                        instructorEntity.getGymUserEntity().getLastName(),
                        instructorEntity.getTrainingTypeEntity().getTrainingTypeName()))
                .toList();
    }

    public UpdateCustomerProfileResponseDto mapCustomerEntityToUpdateCustomerResponseDto(CustomerEntity updatedCustomer) {
        return new UpdateCustomerProfileResponseDto(
                updatedCustomer.getGymUserEntity().getUserName(),
                updatedCustomer.getGymUserEntity().getFirstName(),
                updatedCustomer.getGymUserEntity().getLastName(),
                updatedCustomer.getDateOfBirth(),
                updatedCustomer.getAddress(),
                updatedCustomer.getGymUserEntity().getIsActive(),
                mapInstructorEntitiesToInstructorDtos(updatedCustomer.getInstructors())
        );
    }
}
