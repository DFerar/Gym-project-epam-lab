package com.gym.service;


import com.gym.entity.GymUserEntity;
import com.gym.entity.InstructorEntity;
import com.gym.entity.TrainingType;
import com.gym.entity.TrainingTypeEntity;
import com.gym.exception.InstructorNotFoundException;
import com.gym.exception.TrainingTypeNotFoundException;
import com.gym.exception.UserNotFoundException;
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

    /**
     * Creates a new instructor and saves it in the database, logging the process.
     *
     * @param gymUserEntity  GymUserEntity object for the new instructor.
     * @param specialization TrainingType object for the instructor's specialization.
     * @return InstructorEntity representing the newly created and saved instructor.
     * @throws TrainingTypeNotFoundException When the provided training type is not found.
     */
    @Transactional
    public InstructorEntity createInstructor(GymUserEntity gymUserEntity, TrainingType specialization) {
        GymUserEntity savedUser = gymUserService.createUser(gymUserEntity);
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

    /**
     * Updates instructor and GymUserEntity details in the database.
     *
     * @param userEntity     GymUserEntity object containing the updated details.
     * @param specialization TrainingType for the instructor's updated specialization.
     * @return InstructorEntity representing the updated instructor.
     * @throws InstructorNotFoundException When an instructor with the given username is not found.
     */
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

    /**
     * Retrieves an InstructorEntity based on a provided username.
     *
     * @param username The username associated with the instructor.
     * @return InstructorEntity associated with the username.
     * @throws InstructorNotFoundException When an instructor with the given username is not found.
     */
    public InstructorEntity getInstructorByUsername(String username) {
        InstructorEntity instructorEntity = instructorRepository.findInstructorEntityByGymUserEntityUserName(username);
        if (instructorEntity == null) {
            throw new InstructorNotFoundException("Instructor not found");
        }
        log.info("Got instructor with username: {}", username);
        return instructorEntity;
    }

    /**
     * Changes the activity status of an instructor.
     *
     * @param username The username associated with the instructor.
     * @throws UserNotFoundException When a user with the given username is not found.
     */
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

    /**
     * Retrieves all instructors not currently assigned to a particular customer.
     *
     * @param customerUsername The username of the customer.
     * @return a list of InstructorEntity objects not assigned to the customer.
     */
    public List<InstructorEntity> getInstructorsNotAssignedToCustomerByCustomerUserName(String customerUsername) {
        log.info("Got instructors not assigned to customer:{}", customerUsername);
        return instructorRepository.findUnassignedInstructorsByCustomerUsername(customerUsername);
    }
}
