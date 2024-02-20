package com.gym.instructor;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class InstructorService {
    private final InstructorRepository instructorRepository;

    public Instructor createInstructor(Instructor instructor) {
        log.info("Creating Instructor: {}", instructor);
        return instructorRepository.createInstructor(instructor);
    }

    public Instructor updateInstructor(Instructor newData) {
        if (instructorRepository.getInstructorById(newData.getUserId()) != null) {
            log.info("Updating instructor");
            return instructorRepository.updateInstructor(newData);
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

    public Instructor getInstructorById(Integer instructorId) {
        Instructor instructor = instructorRepository.getInstructorById(instructorId);
        if (instructor != null) {
            log.info("Getting instructor with ID: {}", instructorId);
            return instructor;
        } else {
            log.info("Instructor with ID was not found: {}", instructorId);
            throw new NoSuchElementException("Instructor not found");
        }
    }
}
