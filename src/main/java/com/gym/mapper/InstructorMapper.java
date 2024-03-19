package com.gym.mapper;

import com.gym.entity.CustomerEntity;
import com.gym.entity.GymUserEntity;
import com.gym.entity.InstructorEntity;
import com.gym.entity.TrainingTypeEntity;
import com.gym.requestDto.instructorRequest.CreateInstructorRequestDto;
import com.gym.requestDto.instructorRequest.UpdateInstructorProfileRequestDto;
import com.gym.responseDto.customerResponse.InstructorForCustomerResponseDto;
import com.gym.responseDto.instructorResponse.*;
import com.gym.service.GymUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

import static com.gym.utils.Utils.generatePassword;

@Component
@RequiredArgsConstructor
public class InstructorMapper {
    private final GymUserService gymUserService;

    public GymUserEntity mapCreateInstructorInstructorRequestDtoToUserEntity(CreateInstructorRequestDto
                                                                                     instructorDto) {
        GymUserEntity gymUserEntity = new GymUserEntity();
        gymUserEntity.setFirstName(instructorDto.getFirstName());
        gymUserEntity.setLastName(instructorDto.getLastName());
        gymUserEntity.setPassword(generatePassword());
        gymUserEntity.setUserName(gymUserService.generateUniqueUserName(instructorDto.getFirstName(),
                instructorDto.getLastName()));
        gymUserEntity.setIsActive(true);
        return gymUserEntity;
    }

    public InstructorEntity mapUserEntityToInstructorEntity(TrainingTypeEntity trainingType,
                                                            GymUserEntity gymUserEntity) {
        InstructorEntity instructorEntity = new InstructorEntity();
        instructorEntity.setTrainingTypeEntity(trainingType);
        instructorEntity.setGymUserEntity(gymUserEntity);
        return instructorEntity;
    }

    public CreateInstructorResponseDto mapToResponseDto(GymUserEntity savedUser) {
        return new CreateInstructorResponseDto(savedUser.getUserName(), savedUser.getPassword());
    }

    public GetInstructorProfileResponseDto mapInstructorEntityToGetInstructorResponseDto(InstructorEntity instructorEntity) {
        return new GetInstructorProfileResponseDto(instructorEntity.getGymUserEntity().getFirstName(),
                instructorEntity.getGymUserEntity().getLastName(),
                instructorEntity.getTrainingTypeEntity().getTrainingTypeName(),
                instructorEntity.getGymUserEntity().getIsActive(),
                mapCustomerEntitiesToCustomerDtos(instructorEntity.getCustomers()));
    }

    private List<CustomerForInstructorResponseDto> mapCustomerEntitiesToCustomerDtos(Set<CustomerEntity> customers) {
        return customers.stream()
                .map(customerEntity -> new CustomerForInstructorResponseDto(
                        customerEntity.getGymUserEntity().getUserName(),
                        customerEntity.getGymUserEntity().getFirstName(),
                        customerEntity.getGymUserEntity().getLastName()
                ))
                .toList();
    }

    public GymUserEntity mapUpdateInstructorRequestDtoToUserEntity(UpdateInstructorProfileRequestDto newData) {
        GymUserEntity gymUserEntity = new GymUserEntity();
        gymUserEntity.setUserName(newData.getUserName());
        gymUserEntity.setFirstName(newData.getFirstName());
        gymUserEntity.setLastName(newData.getLastName());
        gymUserEntity.setIsActive(newData.getIsActive());
        return gymUserEntity;
    }

    public UpdateInstructorProfileResponseDto mapInstructorEntityToUpdateInstructorResponseDto(InstructorEntity updatedInstructor) {
        return new UpdateInstructorProfileResponseDto(updatedInstructor.getGymUserEntity().getUserName(),
                updatedInstructor.getGymUserEntity().getFirstName(),
                updatedInstructor.getGymUserEntity().getLastName(),
                updatedInstructor.getTrainingTypeEntity().getTrainingTypeName(),
                updatedInstructor.getGymUserEntity().getIsActive(),
                mapCustomerEntitiesToCustomerDtos(updatedInstructor.getCustomers()));
    }

    public List<GetNotAssignedOnCustomerInstructorsResponseDto> mapInstructorEntitiesToInstructorDtos(List<InstructorEntity> instructorEntities) {
        return instructorEntities.stream()
                .map(instructorEntity -> new GetNotAssignedOnCustomerInstructorsResponseDto(
                        instructorEntity.getGymUserEntity().getUserName(),
                        instructorEntity.getGymUserEntity().getFirstName(),
                        instructorEntity.getGymUserEntity().getLastName(),
                        instructorEntity.getTrainingTypeEntity().getTrainingTypeName()
                ))
                .toList();
    }

    public List<InstructorForCustomerResponseDto> mapInstructorEntitiesToInstructorResponseDto(Set<InstructorEntity> instructorEntities) {
        return instructorEntities.stream()
                .map(instructorEntity -> new InstructorForCustomerResponseDto(
                        instructorEntity.getGymUserEntity().getUserName(),
                        instructorEntity.getGymUserEntity().getFirstName(),
                        instructorEntity.getGymUserEntity().getLastName(),
                        instructorEntity.getTrainingTypeEntity().getTrainingTypeName()
                ))
                .toList();
    }
}
