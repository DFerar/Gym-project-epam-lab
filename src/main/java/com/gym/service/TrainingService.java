package com.gym.service;

import com.gym.entity.CustomerEntity;
import com.gym.entity.InstructorEntity;
import com.gym.entity.TrainingEntity;
import com.gym.entity.TrainingType;
import com.gym.entity.TrainingTypeEntity;
import com.gym.exception.CustomerNotFoundException;
import com.gym.exception.InstructorNotFoundException;
import com.gym.exception.TrainingTypeNotFoundException;
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

    /**
     * Creates a new training record and saves it in the database, logging the process.
     * Additionally, updates the instructors associated with the customer.
     *
     * @param trainingEntity TrainingEntity object to be saved.
     * @throws CustomerNotFoundException     When the customer associated with the training record is not found.
     * @throws InstructorNotFoundException   When the instructor associated with the training record is not found.
     * @throws TrainingTypeNotFoundException When the training type associated with the training record is not found.
     */
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

    /**
     * Retrieves a list of training records for a customer based on specified criteria.
     *
     * @param customerUserName The username of the customer.
     * @param fromDate         The start date for the list of trainings.
     * @param toDate           The end date for the list of trainings.
     * @param instructorName   The name of the instructor of the trainings.
     * @param trainingTypeName The training type of the trainings.
     * @return a list of TrainingEntity objects matching the specified criteria.
     */
    public List<TrainingEntity> getCustomerListOfTrainings(String customerUserName, LocalDate fromDate,
                                                           LocalDate toDate,
                                                           String instructorName, TrainingType trainingTypeName) {
        return trainingRepository.findTrainingsByCustomerAndCriteria(
            customerUserName, fromDate, toDate, instructorName, trainingTypeName
        );
    }

    /**
     * Retrieves a list of training records for an instructor based on specified criteria.
     *
     * @param userName     The username of the instructor.
     * @param fromDate     The start date for the list of trainings.
     * @param toDate       The end date for the list of trainings.
     * @param customerName The name of the customer to retrieve trainings for.
     * @return a list of TrainingEntity objects matching the specified criteria.
     */
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

