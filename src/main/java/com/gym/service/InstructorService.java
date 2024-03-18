package com.gym.service;


import com.gym.dto.InstructorDto;
import com.gym.entity.GymUserEntity;
import com.gym.entity.InstructorEntity;
import com.gym.entity.TrainingType;
import com.gym.entity.TrainingTypeEntity;
import com.gym.mapper.InstructorMapper;
import com.gym.repository.GymUserRepository;
import com.gym.repository.InstructorRepository;
import com.gym.repository.TrainingTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static com.gym.utils.Utils.generatePassword;

@Service
@RequiredArgsConstructor
@Slf4j
public class InstructorService {
    private final InstructorRepository instructorRepository;
    private final GymUserRepository gymUserRepository;
    private final GymUserService gymUserService;
    private final TrainingTypeRepository trainingTypeRepository;
    private final TrainingService trainingService;
    private final AuthenticationService authenticationService;
    private final InstructorMapper instructorMapper;

    @Transactional
    public InstructorEntity createInstructor(GymUserEntity gymUserEntity, TrainingType specialization) {
        GymUserEntity savedUser = gymUserRepository.save(gymUserEntity);
        TrainingTypeEntity trainingType =
                trainingTypeRepository.findByTrainingTypeName(specialization);
        if (trainingType == null) {
            throw new NoSuchElementException("training type not found");
        }
        InstructorEntity instructorEntity = instructorMapper.mapUserEntityToInstructorEntity(trainingType, savedUser);
        log.info("Creating instructor: {}", instructorEntity);
        return instructorEntity;
    }

    public InstructorDto getInstructorById(String loginUserName, String loginPassword, Long instructorId) {
        checkCredentialsMatching(loginUserName, loginPassword);
        InstructorEntity instructorEntity = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new NoSuchElementException("Instructor not found"));
        GymUserEntity gymUserEntity = instructorEntity.getGymUserEntity();
        log.info("Got instructor: {}", instructorId);
        return entityToDto(instructorEntity, gymUserEntity);
    }

    @Transactional
    public InstructorEntity updateInstructor(GymUserEntity userEntity, TrainingType specialization) {
        GymUserEntity updatedUser = gymUserService.updateUser(userEntity);

        TrainingTypeEntity trainingType = trainingTypeRepository.findByTrainingTypeName(specialization);
        trainingType.setTrainingTypeName(specialization);

        InstructorEntity instructorEntity = instructorRepository.findInstructorEntityByGymUserEntityUserName(
                updatedUser.getUserName());
        if (instructorEntity == null) {
            throw new NoSuchElementException("Instructor not found");
        }
        instructorEntity.setTrainingTypeEntity(trainingType);
        InstructorEntity updatedInstructor = instructorRepository.save(instructorEntity);
        log.info("Instructor updated: {}", updatedInstructor);
        return updatedInstructor;
    }

    public InstructorEntity getInstructorByUsername(String username) {
        InstructorEntity instructorEntity = instructorRepository.findInstructorEntityByGymUserEntityUserName(username);
        if (instructorEntity == null) {
            throw new NoSuchElementException("Instructor not found");
        }
        log.info("Got instructor with username: {}", username);
        return instructorEntity;

    }

    @Transactional
    public void changeInstructorPassword(String loginUserName, String loginPassword, Long userId,
                                         String newPassword) {
        checkCredentialsMatching(loginUserName, loginPassword);
        GymUserEntity gymUserEntity = gymUserRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        gymUserEntity.setPassword(newPassword);
        gymUserRepository.save(gymUserEntity);
        log.info("Password changed on user: {}", userId);
    }

    @Transactional
    public void changeInstructorActivity(String loginUserName, String loginPassword, Long userId,
                                         Boolean newActivity) {
        checkCredentialsMatching(loginUserName, loginPassword);
        GymUserEntity gymUserEntity = gymUserRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        gymUserEntity.setIsActive(newActivity);
        gymUserRepository.save(gymUserEntity);
        log.info("Activity changed on user:{}", userId);
    }

    public List<InstructorEntity> getInstructorsNotAssignedToCustomerByCustomerUserName(String customerUsername) {
        log.info("Got instructors not assigned to customer:{}", customerUsername);
        return instructorRepository.findUnassignedInstructorsByCustomerUsername(customerUsername);
    }

    private InstructorDto entityToDto(InstructorEntity savedInstructor, GymUserEntity savedUser) {
        return new InstructorDto(savedInstructor.getId(), savedInstructor.getTrainingTypeEntity().getTrainingTypeName(),
                savedUser.getId(), savedUser.getFirstName(), savedUser.getLastName(), savedUser.getUserName(),
                savedUser.getIsActive());
    }

    private InstructorEntity instructorDtoToInstructorEntity(GymUserEntity savedUser,
                                                             TrainingTypeEntity trainingType) {
        InstructorEntity instructorEntity = new InstructorEntity();
        instructorEntity.setTrainingTypeEntity(trainingType);
        instructorEntity.setGymUserEntity(savedUser);
        return instructorEntity;
    }

    private GymUserEntity instructorDtoToUserEntity(InstructorDto instructorDto) {
        GymUserEntity gymUserEntity = new GymUserEntity();
        gymUserEntity.setFirstName(instructorDto.getFirstName());
        gymUserEntity.setLastName(instructorDto.getLastName());
        gymUserEntity.setPassword(generatePassword());
        gymUserEntity.setUserName(gymUserService.generateUniqueUserName(instructorDto.getFirstName(),
                instructorDto.getLastName()));
        gymUserEntity.setIsActive(instructorDto.getIsActive());
        return gymUserEntity;
    }

    private void checkCredentialsMatching(String userName, String password) {
        if (!authenticationService.matchInstructorCredentials(userName, password)) {
            throw new SecurityException("authentication failed");
        }
    }
}
