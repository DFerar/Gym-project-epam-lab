package com.gym.instructor;

import com.gym.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;

import static com.gym.utils.Utils.*;

@Repository
@RequiredArgsConstructor
public class InstructorRepository {
    private final Storage storage;

    public Instructor createInstructor(Instructor instructor) {
        Integer userId = getLastMapObjectId(storage.getInstructorStorage().keySet()) + 1;
        String password = generatePassword();
        instructor.setUserId(userId);
        instructor.setPassword(password);
        String userName = generateUsername(instructor.getFirstName(), instructor.getLastName());
        if (!ifUsernameExists(userName)) {
            instructor.setUserName(userName);
        } else {
            String uniqueUsername = userName + userId;
            instructor.setUserName(uniqueUsername);
        }
        storage.getInstructorStorage().put(userId, instructor);
        try {
            storage.updateDatasource(storage.getStorageData());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return instructor;
    }

    public Instructor getInstructorById(Integer instructorId) {
        return storage.getInstructorStorage().get(instructorId);
    }

    public Instructor updateInstructor(Instructor newData) {
        Instructor instructorToUpdate = getInstructorById(newData.getUserId());
        String newUsername = generateUsername(newData.getFirstName(), newData.getLastName());
        if (!ifUsernameExists(newUsername)) {
            instructorToUpdate.setUserName(newUsername);
        } else {
            String uniqueUsername = newUsername + newData.getUserId();
            instructorToUpdate.setUserName(uniqueUsername);
        }
        instructorToUpdate.setFirstName(newData.getFirstName());
        instructorToUpdate.setLastName(newData.getLastName());
        instructorToUpdate.setSpecialization(newData.getSpecialization());
        instructorToUpdate.setIsActive(newData.getIsActive());
        try {
            storage.updateDatasource(storage.getStorageData());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return instructorToUpdate;
    }

    public void deleteInstructor(Integer instructorId) {
        storage.getInstructorStorage().remove(instructorId);
        try {
            storage.updateDatasource(storage.getStorageData());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean ifUsernameExists(String userName) {
        return storage.getInstructorStorage().values().stream()
                .anyMatch(instructor -> instructor.getUserName().equals(userName));
    }
}
