package com.gym.service;


import com.gym.entity.GymUserEntity;
import com.gym.entity.InstructorEntity;
import com.gym.entity.TrainingType;
import com.gym.entity.TrainingTypeEntity;
import com.gym.exceptionHandler.InstructorNotFoundException;
import com.gym.exceptionHandler.TrainingTypeNotFoundException;
import com.gym.exceptionHandler.UserNotFoundException;
import com.gym.mapper.InstructorMapper;
import com.gym.repository.GymUserRepository;
import com.gym.repository.InstructorRepository;
import com.gym.repository.TrainingTypeRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InstructorService {
    private final InstructorRepository instructorRepository;
    private final GymUserRepository gymUserRepository;
    private final GymUserService gymUserService;
    private final TrainingTypeRepository trainingTypeRepository;
    private final InstructorMapper instructorMapper;

    @Transactional
    public InstructorEntity createInstructor(GymUserEntity gymUserEntity, TrainingType specialization) {
        GymUserEntity savedUser = gymUserRepository.save(gymUserEntity);
        TrainingTypeEntity trainingType =
                trainingTypeRepository.findByTrainingTypeName(specialization);
        if (trainingType == null) {
            throw new TrainingTypeNotFoundException("Training type not found");
        }
        InstructorEntity instructorEntity = instructorMapper.mapUserEntityToInstructorEntity(trainingType, savedUser);
        instructorRepository.save(instructorEntity);
        log.info("Creating instructor: {}", instructorEntity);
        return instructorEntity;
    }

    @Transactional
    public InstructorEntity updateInstructor(GymUserEntity userEntity, TrainingType specialization) {
        GymUserEntity updatedUser = gymUserService.updateUser(userEntity);

        TrainingTypeEntity trainingType = trainingTypeRepository.findByTrainingTypeName(specialization);
        trainingType.setTrainingTypeName(specialization);

        InstructorEntity instructorEntity = instructorRepository.findInstructorEntityByGymUserEntityUserName(
                updatedUser.getUserName());
        if (instructorEntity == null) {
            throw new InstructorNotFoundException("Instructor not found");
        }
        instructorEntity.setTrainingTypeEntity(trainingType);
        instructorEntity.getGymUserEntity().setIsActive(userEntity.getIsActive());
        InstructorEntity updatedInstructor = instructorRepository.save(instructorEntity);
        log.info("Instructor updated: {}", updatedInstructor);
        return updatedInstructor;
    }

    public InstructorEntity getInstructorByUsername(String username) {
        InstructorEntity instructorEntity = instructorRepository.findInstructorEntityByGymUserEntityUserName(username);
        if (instructorEntity == null) {
            throw new InstructorNotFoundException("Instructor not found");
        }
        log.info("Got instructor with username: {}", username);
        return instructorEntity;
    }

    @Transactional
    public void changeInstructorActivity(String username) {
        GymUserEntity gymUserEntity = gymUserRepository.findByUserName(username);
        if (gymUserEntity == null) {
            throw new UserNotFoundException("User not found");
        }
        gymUserEntity.setIsActive(!gymUserEntity.getIsActive());
        gymUserRepository.save(gymUserEntity);
        log.info("Activity changed on user:{}", gymUserEntity);
    }

    public List<InstructorEntity> getInstructorsNotAssignedToCustomerByCustomerUserName(String customerUsername) {
        log.info("Got instructors not assigned to customer:{}", customerUsername);
        return instructorRepository.findUnassignedInstructorsByCustomerUsername(customerUsername);
    }
}
