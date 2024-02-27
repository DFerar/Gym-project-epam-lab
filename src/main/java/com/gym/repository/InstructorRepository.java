package com.gym.repository;

import com.gym.entity.InstructorEntity;
import com.gym.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@RequiredArgsConstructor
public class InstructorRepository {
    private final Storage storage;

    @SneakyThrows
    public InstructorEntity createInstructor(InstructorEntity instructor) {
        InstructorEntity instructorFromBase = storage.addInstructor(instructor);
        storage.updateDatasource();
        return instructorFromBase;
    }

    public InstructorEntity getInstructorById(Integer instructorId) {
        return storage.getInstructorById(instructorId);
    }

    @SneakyThrows
    public InstructorEntity updateInstructor(InstructorEntity instructorToUpdate) {
        storage.updateDatasource();
        return instructorToUpdate;
    }

    @SneakyThrows
    public void deleteInstructor(Integer instructorId) {
        storage.deleteInstructor(instructorId);
        storage.updateDatasource();
    }

    public Set<Integer> getInstructorIds() {
        return storage.getInstructorIds();
    }

    public boolean ifUsernameExists(String userName) {
        return storage.checkIfInstructorUserNameExists(userName);
    }
}
