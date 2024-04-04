package com.gym.mapper;

import static com.gym.utils.Utils.generatePassword;

import com.gym.dto.request.instructor.CreateInstructorRequestDto;
import com.gym.dto.request.instructor.UpdateInstructorProfileRequestDto;
import com.gym.dto.response.customer.InstructorForCustomerResponseDto;
import com.gym.dto.response.instructor.CreateInstructorResponseDto;
import com.gym.dto.response.instructor.CustomerForInstructorResponseDto;
import com.gym.dto.response.instructor.GetInstructorProfileResponseDto;
import com.gym.dto.response.instructor.GetNotAssignedOnCustomerInstructorsResponseDto;
import com.gym.dto.response.instructor.UpdateInstructorProfileResponseDto;
import com.gym.entity.CustomerEntity;
import com.gym.entity.GymUserEntity;
import com.gym.entity.InstructorEntity;
import com.gym.entity.TrainingTypeEntity;
import com.gym.service.GymUserService;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstructorMapper {
    private final GymUserService gymUserService;

    /**
     * Maps a CreateInstructorRequestDto object to a GymUserEntity object.
     *
     * @param instructorDto CreateInstructorRequestDto object containing instructor details.
     * @return GymUserEntity representing the instructor.
     */
    public GymUserEntity mapCreateInstructorRequestDtoToUserEntity(CreateInstructorRequestDto
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

    /**
     * Maps a GymUserEntity object and a TrainingTypeEntity object to an InstructorEntity object.
     *
     * @param trainingType  TrainingTypeEntity object.
     * @param gymUserEntity GymUserEntity object.
     * @return InstructorEntity created from the provided parameters.
     */
    public InstructorEntity mapUserEntityToInstructorEntity(TrainingTypeEntity trainingType,
                                                            GymUserEntity gymUserEntity) {
        InstructorEntity instructorEntity = new InstructorEntity();
        instructorEntity.setTrainingTypeEntity(trainingType);
        instructorEntity.setGymUserEntity(gymUserEntity);
        return instructorEntity;
    }

    /**
     * Maps a GymUserEntity object to a CreateInstructorResponseDto object.
     *
     * @param savedUser GymUserEntity object.
     * @return CreateInstructorResponseDto containing the mapped values.
     */
    public CreateInstructorResponseDto mapToResponseDto(GymUserEntity savedUser) {
        return new CreateInstructorResponseDto(savedUser.getUserName(), savedUser.getPassword());
    }

    /**
     * Maps an InstructorEntity object to a GetInstructorProfileResponseDto object.
     *
     * @param instructorEntity InstructorEntity object.
     * @return GetInstructorProfileResponseDto containing the mapped values.
     */
    public GetInstructorProfileResponseDto mapInstructorEntityToGetInstructorResponseDto(
        InstructorEntity instructorEntity) {
        return new GetInstructorProfileResponseDto(instructorEntity.getGymUserEntity().getFirstName(),
            instructorEntity.getGymUserEntity().getLastName(),
            instructorEntity.getTrainingTypeEntity().getTrainingTypeName(),
            instructorEntity.getGymUserEntity().getIsActive(),
            mapCustomerEntitiesToCustomerDtos(instructorEntity.getCustomers()));
    }

    /**
     * Maps an UpdateInstructorProfileRequestDto object to a GymUserEntity object.
     *
     * @param newData UpdateInstructorProfileRequestDto object.
     * @return GymUserEntity containing the updated data.
     */
    public GymUserEntity mapUpdateInstructorRequestDtoToUserEntity(UpdateInstructorProfileRequestDto newData) {
        GymUserEntity gymUserEntity = new GymUserEntity();
        gymUserEntity.setUserName(newData.getUserName());
        gymUserEntity.setFirstName(newData.getFirstName());
        gymUserEntity.setLastName(newData.getLastName());
        gymUserEntity.setIsActive(newData.getIsActive());
        return gymUserEntity;
    }

    /**
     * Maps an InstructorEntity object to an UpdateInstructorProfileResponseDto object.
     *
     * @param updatedInstructor InstructorEntity object.
     * @return UpdateInstructorProfileResponseDto containing the mapped values.
     */
    public UpdateInstructorProfileResponseDto mapInstructorEntityToUpdateInstructorResponseDto(
        InstructorEntity updatedInstructor) {
        return new UpdateInstructorProfileResponseDto(updatedInstructor.getGymUserEntity().getUserName(),
            updatedInstructor.getGymUserEntity().getFirstName(),
            updatedInstructor.getGymUserEntity().getLastName(),
            updatedInstructor.getTrainingTypeEntity().getTrainingTypeName(),
            updatedInstructor.getGymUserEntity().getIsActive(),
            mapCustomerEntitiesToCustomerDtos(updatedInstructor.getCustomers()));
    }

    /**
     * Maps a list of InstructorEntity objects to a list of GetNotAssignedOnCustomerInstructorsResponseDto objects.
     *
     * @param instructorEntities List of InstructorEntity objects.
     * @return List of GetNotAssignedOnCustomerInstructorsResponseDto containing the mapped values.
     */
    public List<GetNotAssignedOnCustomerInstructorsResponseDto> mapInstructorEntitiesToInstructorDtos(
        List<InstructorEntity> instructorEntities) {
        return instructorEntities.stream()
            .map(instructorEntity -> new GetNotAssignedOnCustomerInstructorsResponseDto(
                instructorEntity.getGymUserEntity().getUserName(),
                instructorEntity.getGymUserEntity().getFirstName(),
                instructorEntity.getGymUserEntity().getLastName(),
                instructorEntity.getTrainingTypeEntity().getTrainingTypeName()
            ))
            .toList();
    }

    /**
     * Maps a set of InstructorEntity objects to a list of InstructorForCustomerResponseDto objects.
     *
     * @param instructorEntities Set of InstructorEntity objects.
     * @return List of InstructorForCustomerResponseDto containing the mapped values.
     */
    public List<InstructorForCustomerResponseDto> mapInstructorEntitiesToInstructorResponseDto(
        Set<InstructorEntity> instructorEntities) {
        return instructorEntities.stream()
            .map(instructorEntity -> new InstructorForCustomerResponseDto(
                instructorEntity.getGymUserEntity().getUserName(),
                instructorEntity.getGymUserEntity().getFirstName(),
                instructorEntity.getGymUserEntity().getLastName(),
                instructorEntity.getTrainingTypeEntity().getTrainingTypeName()
            ))
            .toList();
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
}
