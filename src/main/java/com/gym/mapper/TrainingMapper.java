package com.gym.mapper;

import com.gym.dto.request.training.CreateTrainingRequestDto;
import com.gym.dto.response.training.CustomerTrainingsResponseDto;
import com.gym.dto.response.training.InstructorTrainingsResponseDto;
import com.gym.dto.response.training.TrainingTypeResponseDto;
import com.gym.entity.CustomerEntity;
import com.gym.entity.GymUserEntity;
import com.gym.entity.InstructorEntity;
import com.gym.entity.TrainingEntity;
import com.gym.entity.TrainingTypeEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingMapper {

    /**
     * Maps TrainingEntity objects to CustomerTrainingsResponseDto objects for customer training records.
     *
     * @param trainingEntities List of TrainingEntity objects to be mapped.
     * @return List of CustomerTrainingsResponseDto.
     */
    public List<CustomerTrainingsResponseDto> mapCustomerTrainingEntitiesToTrainingDtos(
        List<TrainingEntity> trainingEntities) {
        return trainingEntities.stream()
            .map(trainingEntity -> new CustomerTrainingsResponseDto(
                trainingEntity.getTrainingName(),
                trainingEntity.getTrainingDate(),
                trainingEntity.getTrainingType().getTrainingTypeName(),
                trainingEntity.getTrainingDuration(),
                trainingEntity.getInstructor().getGymUserEntity().getUserName()))
            .toList();
    }

    /**
     * Maps TrainingEntity objects to InstructorTrainingsResponseDto objects for instructor training records.
     *
     * @param trainingEntities List of TrainingEntity objects to be mapped.
     * @return List of InstructorTrainingsResponseDto.
     */
    public List<InstructorTrainingsResponseDto> mapInstructorTrainingEntitiesToTrainingDtos(
        List<TrainingEntity> trainingEntities) {
        return trainingEntities.stream()
            .map(trainingEntity -> new InstructorTrainingsResponseDto(
                trainingEntity.getTrainingName(),
                trainingEntity.getTrainingDate(),
                trainingEntity.getTrainingType().getTrainingTypeName(),
                trainingEntity.getTrainingDuration(),
                trainingEntity.getCustomer().getGymUserEntity().getUserName()))
            .toList();
    }

    /**
     * Maps CreateTrainingRequestDto to TrainingEntity for creating new training records.
     *
     * @param trainingRequestDto CreateTrainingRequestDto object containing details for new training record.
     * @return TrainingEntity object created from the provided data.
     */
    public TrainingEntity mapCreateTrainingRequestDtoToTrainingEntity(CreateTrainingRequestDto trainingRequestDto) {
        GymUserEntity gymUserCustomerEntity = new GymUserEntity();
        gymUserCustomerEntity.setUserName(trainingRequestDto.getCustomerUserName());

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setGymUserEntity(gymUserCustomerEntity);

        GymUserEntity gymUserInstructorEntity = new GymUserEntity();
        gymUserInstructorEntity.setUserName(trainingRequestDto.getInstructorUserName());

        InstructorEntity instructorEntity = new InstructorEntity();
        instructorEntity.setGymUserEntity(gymUserInstructorEntity);

        TrainingEntity trainingEntity = new TrainingEntity();
        trainingEntity.setCustomer(customerEntity);
        trainingEntity.setInstructor(instructorEntity);
        trainingEntity.setTrainingDate(trainingRequestDto.getTrainingDate());
        trainingEntity.setTrainingDuration(trainingRequestDto.getTrainingDuration());
        trainingEntity.setTrainingName(trainingRequestDto.getTrainingName());
        return trainingEntity;
    }

    /**
     * Maps TrainingTypeEntity objects to TrainingTypeResponseDto for retrieving training type details.
     *
     * @param trainingTypeEntities List of TrainingTypeEntity objects to be mapped.
     * @return List of TrainingTypeResponseDto.
     */
    public List<TrainingTypeResponseDto> mapTrainingTypeEntitiesToTrainingTypeResponseDto(
        List<TrainingTypeEntity> trainingTypeEntities) {
        return trainingTypeEntities.stream()
            .map(trainingType -> new TrainingTypeResponseDto(
                trainingType.getId(),
                trainingType.getTrainingTypeName()
            ))
            .toList();
    }
}
