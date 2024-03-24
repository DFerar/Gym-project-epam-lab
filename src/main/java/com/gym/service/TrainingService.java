package com.gym.service;

import com.gym.entity.*;
import com.gym.repository.CustomerRepository;
import com.gym.repository.InstructorRepository;
import com.gym.repository.TrainingRepository;
import com.gym.repository.TrainingTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingService {
    private final TrainingRepository trainingRepository;
    private final CustomerRepository customerRepository;
    private final InstructorRepository instructorRepository;
    private final TrainingTypeRepository trainingTypeRepository;

    @Transactional
    public void createTraining(TrainingEntity trainingEntity) {
        CustomerEntity customerEntity = customerRepository.findCustomerEntityByGymUserEntityUserName(
                trainingEntity.getCustomer().getGymUserEntity().getUserName());
        if (customerEntity == null) {
            throw new NoSuchElementException("Customer not found");
        }
        trainingEntity.setCustomer(customerEntity);

        InstructorEntity instructorEntity = instructorRepository.findInstructorEntityByGymUserEntityUserName(
                trainingEntity.getInstructor().getGymUserEntity().getUserName());
        if (instructorEntity == null) {
            throw new NoSuchElementException("Customer not found");
        }
        trainingEntity.setInstructor(instructorEntity);

        TrainingTypeEntity trainingTypeEntity = trainingTypeRepository.findByTrainingTypeName(
                instructorEntity.getTrainingTypeEntity().getTrainingTypeName());
        if (trainingTypeEntity == null) {
            throw new NoSuchElementException("Customer not found");
        }
        trainingEntity.setTrainingType(trainingTypeEntity);

        TrainingEntity savedTraining = trainingRepository.save(trainingEntity);
        updateInstructors(customerEntity, instructorEntity);
        log.info("Instructors of customers were updated: {}", customerEntity.getId());
        log.info("Created training:{}", savedTraining);
    }

    public List<TrainingEntity> getCustomerListOfTrainings(String customerUserName, Date fromDate, Date toDate,
                                                        String instructorName, TrainingType trainingTypeName) {
        return trainingRepository.findTrainingsByCustomerAndCriteria(
               customerUserName, fromDate, toDate, instructorName, trainingTypeName
        );
    }

    public List<TrainingEntity> getInstructorListOfTrainings(String userName, Date fromDate,
                                                          Date toDate, String customerName) {
        return trainingRepository.findTrainingsByInstructorAndCriteria(
                userName, fromDate, toDate, customerName
        );
    }

    private void updateInstructors(CustomerEntity customerEntity, InstructorEntity instructorEntity) {
        Set<InstructorEntity> instructorEntities = customerEntity.getInstructors();
        instructorEntities.add(instructorEntity);
        customerEntity.setInstructors(instructorEntities);
        customerRepository.save(customerEntity);
    }
}

