package com.gym.service;


import static com.gym.utils.Utils.generatePassword;
import static com.gym.utils.Utils.generateUsername;
import static com.gym.utils.Utils.getLastMapObjectId;

import com.gym.entity.InstructorEntity;
import com.gym.repository.InstructorRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InstructorService {
    private final InstructorRepository instructorRepository;

    public InstructorEntity createInstructor(InstructorEntity instructor) {
        int userId = getLastMapObjectId(instructorRepository.getInstructorIds()) + 1;
        String password = generatePassword();
        instructor.setUserId(userId);
        instructor.setPassword(password);
        String userName = generateUniqueInstructorName(instructor.getFirstName(), instructor.getLastName(), userId);
        instructor.setUserName(userName);
        log.info("Creating Instructor: {}", instructor);
        return instructorRepository.createInstructor(instructor);
    }

    public InstructorEntity updateInstructor(InstructorEntity newData) {
        if (instructorRepository.getInstructorById(newData.getUserId()) != null) {
            InstructorEntity instructorToUpdate = getInstructorById(newData.getUserId());
            String newUsername = generateUniqueInstructorName(newData.getFirstName(), newData.getLastName(), newData.getUserId());
            instructorToUpdate.setUserName(newUsername);
            instructorToUpdate.setFirstName(newData.getFirstName());
            instructorToUpdate.setLastName(newData.getLastName());
            instructorToUpdate.setSpecialization(newData.getSpecialization());
            instructorToUpdate.setIsActive(newData.getIsActive());
            log.info("Updating instructor with ID {}: ", newData.getUserId());
            return instructorRepository.updateInstructor(instructorToUpdate);
        } else {
            log.warn("Instructor not found");
            throw new NoSuchElementException("Instructor not found");
        }
    }

    public void deleteInstructor(Integer instructorId) {
        if (instructorRepository.getInstructorById(instructorId) != null) {
            log.info("deleting instructor with ID: {}" , instructorId);
            instructorRepository.deleteInstructor(instructorId);
        } else {
            log.info("Instructor with ID was not found: {}", instructorId);
            throw new NoSuchElementException("Instructor not found");
        }
    }

    public InstructorEntity getInstructorById(Integer instructorId) {
        InstructorEntity instructor = instructorRepository.getInstructorById(instructorId);
        if (instructor != null) {
            log.info("Getting instructor with ID: {}", instructorId);
            return instructor;
        } else {
            log.info("Instructor with ID was not found: {}", instructorId);
            throw new NoSuchElementException("Instructor not found");
        }
    }

    private String generateUniqueInstructorName(String firstName, String lastName, Integer userId) {
        String userName = generateUsername(firstName, lastName);
        if (!(instructorRepository.ifUsernameExists(userName))) {
            return userName;
        } else {
            return userName + userId;
        }
    }
}
