package com.gym.mapper;

import static com.gym.utils.Utils.generatePassword;

import com.gym.dto.request.customer.CreateCustomerRequestDto;
import com.gym.dto.request.customer.UpdateCustomerProfileRequestDto;
import com.gym.dto.response.customer.CreateCustomerResponseDto;
import com.gym.dto.response.customer.GetCustomerProfileResponseDto;
import com.gym.dto.response.customer.InstructorForCustomerResponseDto;
import com.gym.dto.response.customer.UpdateCustomerProfileResponseDto;
import com.gym.entity.CustomerEntity;
import com.gym.entity.GymUserEntity;
import com.gym.entity.InstructorEntity;
import com.gym.service.GymUserService;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerMapper {
    private final GymUserService gymUserService;

    /**
     * This is mapCreateCustomerRequestDtoToUserEntity method.
     * It maps CreateCustomerRequestDto object to UserEntity object.
     *
     * @param customer CreateCustomerRequestDto object containing details for mapping.
     * @return {@code GymUserEntity} Mapped user entity according to the request DTO.
     */
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

    /**
     * This is mapCreateCustomerRequestDtoToCustomerEntity method.
     * It maps CreateCustomerRequestDto object to CustomerEntity object.
     *
     * @param customerDto CreateCustomerRequestDto object containing customer details.
     * @return {@code CustomerEntity} Mapped customer entity object according to the request DTO.
     */
    public CustomerEntity mapCreateCustomerRequestDtoToCustomerEntity(CreateCustomerRequestDto customerDto) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setAddress(customerDto.getAddress());
        customerEntity.setDateOfBirth(customerDto.getDateOfBirth());
        return customerEntity;
    }

    /**
     * This is mapCustomerEntityToCreateResponseDto method.
     * It maps UserEntity object to CreateCustomerResponseDto object.
     *
     * @param savedUser UserEntity object obtained after saving the customer details to database.
     * @return {@code CreateCustomerResponseDto} Response DTO according to the saved user entity.
     */
    public CreateCustomerResponseDto mapCustomerEntityToCreateResponseDto(GymUserEntity savedUser) {
        return new CreateCustomerResponseDto(savedUser.getUserName(),
            savedUser.getPassword());
    }

    /**
     * This is mapCustomerEntityToGetCustomerResponseDto method.
     * It maps CustomerEntity object to GetCustomerProfileResponseDto object.
     *
     * @param customerEntity CustomerEntity object containing required details.
     * @return {@code GetCustomerProfileResponseDto} Mapped customer profile response DTO.
     */
    public GetCustomerProfileResponseDto mapCustomerEntityToGetCustomerResponseDto(CustomerEntity customerEntity) {
        return new GetCustomerProfileResponseDto(
            customerEntity.getGymUserEntity().getFirstName(), customerEntity.getGymUserEntity().getLastName(),
            customerEntity.getDateOfBirth(), customerEntity.getAddress(),
            customerEntity.getGymUserEntity().getIsActive(),
            mapInstructorEntitiesToInstructorDtos(customerEntity.getInstructors())
        );
    }

    /**
     * This is mapUpdateCustomerRequestDtoToUserEntity method.
     * It maps UpdateCustomerProfileRequestDto object to UserEntity object.
     *
     * @param newData UpdateCustomerProfileRequestDto object containing new details for the customer.
     * @return {@code GymUserEntity} Mapped user entity according to the provided update details.
     */
    public GymUserEntity mapUpdateCustomerRequestDtoToUserEntity(UpdateCustomerProfileRequestDto newData) {
        GymUserEntity gymUserEntity = new GymUserEntity();
        gymUserEntity.setUserName(newData.getUserName());
        gymUserEntity.setFirstName(newData.getFirstName());
        gymUserEntity.setLastName(newData.getLastName());
        gymUserEntity.setIsActive(newData.getIsActive());
        return gymUserEntity;
    }

    /**
     * This is mapUpdateCustomerRequestDtoToCustomerEntity method.
     * It maps UpdateCustomerProfileRequestDto object to CustomerEntity object.
     *
     * @param newData UpdateCustomerProfileRequestDto object containing new customer details.
     * @return {@code CustomerEntity} Mapped customer entity according to the provided updation details.
     */
    public CustomerEntity mapUpdateCustomerRequestDtoToCustomerEntity(UpdateCustomerProfileRequestDto newData) {
        GymUserEntity gymUserEntity = new GymUserEntity();
        gymUserEntity.setUserName(newData.getUserName());
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setDateOfBirth(newData.getDateOfBirth());
        customerEntity.setAddress(newData.getAddress());
        return customerEntity;
    }

    /**
     * This is mapCustomerEntityToUpdateCustomerResponseDto method.
     * It maps CustomerEntity object to UpdateCustomerProfileResponseDto object.
     *
     * @param updatedCustomer CustomerEntity object containing updated details.
     * @return {@code UpdateCustomerProfileResponseDto} Mapped response DTO according to the updated customer entity.
     */
    public UpdateCustomerProfileResponseDto mapCustomerEntityToUpdateCustomerResponseDto(
        CustomerEntity updatedCustomer) {
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

    private List<InstructorForCustomerResponseDto> mapInstructorEntitiesToInstructorDtos(
        Set<InstructorEntity> instructors) {
        return instructors.stream()
            .map(instructorEntity -> new InstructorForCustomerResponseDto(
                instructorEntity.getGymUserEntity().getUserName(),
                instructorEntity.getGymUserEntity().getFirstName(),
                instructorEntity.getGymUserEntity().getLastName(),
                instructorEntity.getTrainingTypeEntity().getTrainingTypeName()))
            .toList();
    }
}
