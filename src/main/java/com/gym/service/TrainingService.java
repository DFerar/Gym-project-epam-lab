package com.gym.service;

import com.gym.entity.CustomerEntity;
import com.gym.entity.InstructorEntity;
import com.gym.entity.TrainingEntity;
import com.gym.entity.TrainingType;
import com.gym.entity.TrainingTypeEntity;
import com.gym.exceptionHandler.CustomerNotFoundException;
import com.gym.exceptionHandler.InstructorNotFoundException;
import com.gym.exceptionHandler.TrainingTypeNotFoundException;
import com.gym.repository.CustomerRepository;
import com.gym.repository.InstructorRepository;
import com.gym.repository.TrainingRepository;
import com.gym.repository.TrainingTypeRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


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
            throw new CustomerNotFoundException("Customer not found");
        }
        trainingEntity.setCustomer(customerEntity);

        InstructorEntity instructorEntity = instructorRepository.findInstructorEntityByGymUserEntityUserName(
                trainingEntity.getInstructor().getGymUserEntity().getUserName());
        if (instructorEntity == null) {
            throw new InstructorNotFoundException("Instructor not found");
        }
        trainingEntity.setInstructor(instructorEntity);

        TrainingTypeEntity trainingTypeEntity = trainingTypeRepository.findByTrainingTypeName(
                instructorEntity.getTrainingTypeEntity().getTrainingTypeName());
        if (trainingTypeEntity == null) {
            throw new TrainingTypeNotFoundException("Training Type not found");
        }
        trainingEntity.setTrainingType(trainingTypeEntity);

        TrainingEntity savedTraining = trainingRepository.save(trainingEntity);
        updateInstructors(customerEntity, instructorEntity);
        log.info("Instructors of customers were updated: {}", customerEntity.getId());
        log.info("Created training:{}", savedTraining);
    }

    public List<TrainingEntity> getCustomerListOfTrainings(String customerUserName, LocalDate fromDate, LocalDate toDate,
                                                           String instructorName, TrainingType trainingTypeName) {
        return trainingRepository.findTrainingsByCustomerAndCriteria(
               customerUserName, fromDate, toDate, instructorName, trainingTypeName
        );
    }

    public List<TrainingEntity> getInstructorListOfTrainings(String userName, LocalDate fromDate,
                                                          LocalDate toDate, String customerName) {
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

