package com.gym.service;


import com.gym.dto.InstructorDto;
import com.gym.dto.TrainingDto;
import com.gym.entity.GymUserEntity;
import com.gym.entity.InstructorEntity;
import com.gym.entity.TrainingTypeEntity;
import com.gym.repository.GymUserRepository;
import com.gym.repository.InstructorRepository;
import com.gym.repository.TrainingTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
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

    @Transactional
    public InstructorDto createInstructor(InstructorDto instructorDto) {
        GymUserEntity gymUserEntity = instructorDtoToUserEntity(instructorDto);
        GymUserEntity savedUser = gymUserRepository.save(gymUserEntity);
        TrainingTypeEntity trainingType =
                trainingTypeRepository.findByTrainingTypeName(instructorDto.getSpecialization());
        if (trainingType == null) {
            throw new NoSuchElementException("training type not found");
        }
        InstructorEntity instructorEntity = instructorDtoToInstructorEntity(savedUser, trainingType);
        InstructorEntity savedInstructor = instructorRepository.save(instructorEntity);
        log.info("Creating instructor: {}", instructorDto);
        return entityToDto(savedInstructor, savedUser);
    }

    public InstructorDto getInstructorById(String loginUserName, String loginPassword, Integer instructorId) {
        checkCredentialsMatching(loginUserName, loginPassword);
        InstructorEntity instructorEntity = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new NoSuchElementException("Instructor not found"));
        GymUserEntity gymUserEntity = instructorEntity.getGymUserEntity();
        log.info("Got instructor: {}", instructorId);
        return entityToDto(instructorEntity, gymUserEntity);
    }

    @Transactional
    public InstructorDto updateInstructor(String loginUserName, String loginPassword, InstructorDto newData) {
        checkCredentialsMatching(loginUserName, loginPassword);
        GymUserEntity updatedUser = gymUserService.updateUser(newData.getUserId(), newData.getFirstName(),
                newData.getLastName(), newData.getIsActive());

        TrainingTypeEntity trainingType = trainingTypeRepository.findByTrainingTypeName(newData.getSpecialization());
        trainingType.setTrainingTypeName(newData.getSpecialization());

        InstructorEntity instructorEntity = instructorRepository.findById(newData.getId())
                .orElseThrow(() -> new NoSuchElementException("Instructor not found"));
        instructorEntity.setTrainingTypeEntity(trainingType);
        InstructorEntity updatedInstructor = instructorRepository.save(instructorEntity);
        log.info("Instructor updated: {}", newData);
        return entityToDto(updatedInstructor, updatedUser);
    }

    public InstructorDto getInstructorByUsername(String loginUserName, String loginPassword, String username) {
        checkCredentialsMatching(loginUserName, loginPassword);
        InstructorEntity instructorEntity = instructorRepository.findInstructorEntityByGymUserEntity_UserName(username);
        if (instructorEntity != null) {
            log.info("Got instructor with username: {}", username);
            return entityToDto(instructorEntity, instructorEntity.getGymUserEntity());
        } else {
            throw new NoSuchElementException("Instructor not found");
        }
    }

    @Transactional
    public void changeInstructorPassword(String loginUserName, String loginPassword, Integer userId,
                                         String newPassword) {
        checkCredentialsMatching(loginUserName, loginPassword);
        GymUserEntity gymUserEntity = gymUserRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        gymUserEntity.setPassword(newPassword);
        gymUserRepository.save(gymUserEntity);
        log.info("Password changed on user: {}", userId);
    }

    @Transactional
    public void changeInstructorActivity(String loginUserName, String loginPassword, Integer userId,
                                         Boolean newActivity) {
        checkCredentialsMatching(loginUserName, loginPassword);
        GymUserEntity gymUserEntity = gymUserRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        gymUserEntity.setIsActive(newActivity);
        gymUserRepository.save(gymUserEntity);
        log.info("Activity changed on user:{}", userId);
    }

    @Transactional
    public List<TrainingDto> getInstructorTrainings(String loginUserName, String loginPassword, String instructorName,
                                                    Date fromDate,
                                                    Date toDate, String customerName) {
        checkCredentialsMatching(loginUserName, loginPassword);
        InstructorEntity instructorEntity =
                instructorRepository.findInstructorEntityByGymUserEntity_UserName(instructorName);
        if (instructorEntity != null) {
            log.info("Got instructors trainings:{}", instructorName);
            return trainingService.getInstructorListOfTrainings(instructorEntity, fromDate, toDate,
                    customerName);
        } else {
            throw new NoSuchElementException("Instructor not found");
        }
    }

    public List<InstructorDto> getInstructorsNotAssignedToCustomerByCustomerUserName(String loginUserName,
                                                                                     String loginPassword,
                                                                                     String customerUsername) {
        checkCredentialsMatching(loginUserName, loginPassword);
        log.info("Got instructors not assigned to customer:{}", customerUsername);
        return instructorRepository.findUnassignedInstructorsByCustomerUsername(customerUsername)
                .stream()
                .map(entity -> entityToDto(entity, entity.getGymUserEntity()))
                .toList();
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
