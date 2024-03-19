package com.gym.mapper;

import com.gym.entity.*;
import com.gym.requestDto.trainingRequest.CreateTrainingRequestDto;
import com.gym.responseDto.trainingResponse.CustomerTrainingsResponseDto;
import com.gym.responseDto.trainingResponse.InstructorTrainingsResponseDto;
import com.gym.responseDto.trainingResponse.TrainingTypeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TrainingMapper {

    public List<CustomerTrainingsResponseDto> mapCustomerTrainingEntitiesToTrainingDtos(List<TrainingEntity> trainingEntities) {
        return trainingEntities.stream()
                .map(trainingEntity -> new CustomerTrainingsResponseDto(
                        trainingEntity.getTrainingName(),
                        trainingEntity.getTrainingDate(),
                        trainingEntity.getTrainingType().getTrainingTypeName(),
                        trainingEntity.getTrainingDuration(),
                        trainingEntity.getInstructor().getGymUserEntity().getUserName()))
                .toList();
    }

    public List<InstructorTrainingsResponseDto> mapInstructorTrainingEntitiesToTrainingDtos(List<TrainingEntity> trainingEntities) {
        return trainingEntities.stream()
                .map(trainingEntity -> new InstructorTrainingsResponseDto(
                        trainingEntity.getTrainingName(),
                        trainingEntity.getTrainingDate(),
                        trainingEntity.getTrainingType().getTrainingTypeName(),
                        trainingEntity.getTrainingDuration(),
                        trainingEntity.getCustomer().getGymUserEntity().getUserName()))
                .toList();
    }

    public TrainingEntity mapCreateTrainingRequestDtoToTrainingEntity(CreateTrainingRequestDto trainingRequestDto) {
        GymUserEntity gymUserCustomerEntity = new GymUserEntity();
        gymUserCustomerEntity.setUserName(trainingRequestDto.getCustomerUserName());

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setGymUserEntity(gymUserCustomerEntity);

        GymUserEntity gymUserInstructorEntity = new GymUserEntity();
        gymUserInstructorEntity.setUserName(trainingRequestDto.getInstructorUserName());

        InstructorEntity instructorEntity = new InstructorEntity();
        instructorEntity.setGymUserEntity(gymUserInstructorEntity);

        TrainingTypeEntity trainingTypeEntity = new TrainingTypeEntity();
        trainingTypeEntity.setTrainingTypeName(trainingRequestDto.getTrainingName());

        TrainingEntity trainingEntity = new TrainingEntity();
        trainingEntity.setCustomer(customerEntity);
        trainingEntity.setInstructor(instructorEntity);
        trainingEntity.setTrainingType(trainingTypeEntity);
        trainingEntity.setTrainingDate(trainingRequestDto.getTrainingDate());
        trainingEntity.setTrainingDuration(trainingRequestDto.getTrainingDuration());
        trainingEntity.setTrainingName(trainingEntity.getTrainingName());
        return trainingEntity;
    }

    public List<TrainingTypeResponseDto> mapTrainingTypeEntitiesToTrainingTypeResponseDto(List<TrainingTypeEntity> trainingTypeEntities) {
        return trainingTypeEntities.stream()
                .map(trainingType -> new TrainingTypeResponseDto(
                        trainingType.getId(),
                        trainingType.getTrainingTypeName()
                ))
                .toList();
    }
}
