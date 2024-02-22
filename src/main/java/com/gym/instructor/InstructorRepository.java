package com.gym.instructor;

import com.gym.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class InstructorRepository {
    private final Storage storage;

    @SneakyThrows
    public Instructor createInstructor(Instructor instructor) {
        storage.getInstructorStorage().put(instructor.getUserId(), instructor);
        storage.updateDatasource();
        return instructor;
    }

    public Instructor getInstructorById(Integer instructorId) {
        return storage.getInstructorStorage().get(instructorId);
    }

    @SneakyThrows
    public Instructor updateInstructor(Instructor instructorToUpdate) {
        storage.updateDatasource();
        return instructorToUpdate;
    }

    @SneakyThrows
    public void deleteInstructor(Integer instructorId) {
        storage.getInstructorStorage().remove(instructorId);
        storage.updateDatasource();
    }

    public Map<Integer, Instructor> getInstructorStorage() {
        return storage.getInstructorStorage();
    }

    public boolean ifUsernameExists(String userName) {
        return storage.getInstructorStorage().values().stream()
                .anyMatch(instructor -> instructor.getUserName().equals(userName));
    }
}
