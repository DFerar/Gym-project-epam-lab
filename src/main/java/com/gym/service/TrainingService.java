package com.gym.service;

import com.gym.dto.TrainingDto;
import com.gym.entity.CustomerEntity;
import com.gym.entity.InstructorEntity;
import com.gym.entity.TrainingEntity;
import com.gym.entity.TrainingTypeEntity;
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
    private final AuthenticationService authenticationService;

    @Transactional
    public TrainingDto createTraining(String userName, String password, TrainingDto trainingDto) {
        checkInstructorCredentials(userName, password);
        TrainingEntity trainingEntity = new TrainingEntity();
        trainingEntity.setTrainingName(trainingDto.getTrainingName());
        trainingEntity.setTrainingDate(trainingDto.getTrainingDate());
        trainingEntity.setTrainingDuration(trainingDto.getTrainingDuration());

        CustomerEntity customerEntity = customerRepository.findById(trainingDto.getCustomerId())
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));
        trainingEntity.setCustomer(customerEntity);

        InstructorEntity instructorEntity = instructorRepository.findById(trainingDto.getInstructorId())
                .orElseThrow(() -> new NoSuchElementException("Instructor not found"));
        trainingEntity.setInstructor(instructorEntity);

        TrainingTypeEntity trainingTypeEntity = trainingTypeRepository.findById(trainingDto.getTrainingTypeId())
                .orElseThrow(() -> new NoSuchElementException("Training type not found"));
        trainingEntity.setTrainingType(trainingTypeEntity);

        TrainingEntity savedTraining = trainingRepository.save(trainingEntity);
        updateInstructors(customerEntity, instructorEntity);
        log.info("Instructors of customers were updated: {}", trainingDto.getCustomerId());
        log.info("Created training:{}", trainingDto);
        return entityToDto(savedTraining);
    }

    public List<TrainingDto> getCustomerListOfTrainings(CustomerEntity customerEntity, Date fromDate, Date toDate,
                                                        String instructorName, String trainingTypeName) {
        List<TrainingEntity> trainingEntities = trainingRepository.findTrainingsByCustomerAndCriteria(
                customerEntity.getId(), fromDate, toDate, instructorName, trainingTypeName
        );
        return trainingEntities.stream()
                .map(this::entityToDto)
                .toList();
    }

    public List<TrainingDto> getInstructorListOfTrainings(InstructorEntity instructorEntity, Date fromDate,
                                                          Date toDate, String customerName) {
        List<TrainingEntity> trainingEntities = trainingRepository.findTrainingsByInstructorAndCriteria(
                instructorEntity.getId(), fromDate, toDate, customerName
        );
        return trainingEntities.stream()
                .map(this::entityToDto)
                .toList();
    }

    private TrainingDto entityToDto(TrainingEntity savedTraining) {
        return new TrainingDto(savedTraining.getId(), savedTraining.getCustomer().getId(),
                savedTraining.getInstructor().getId(), savedTraining.getTrainingName(),
                savedTraining.getTrainingType().getId(),
                savedTraining.getTrainingDate(), savedTraining.getTrainingDuration());
    }

    private void updateInstructors(CustomerEntity customerEntity, InstructorEntity instructorEntity) {
        Set<InstructorEntity> instructorEntities = customerEntity.getInstructors();
        instructorEntities.add(instructorEntity);
        customerEntity.setInstructors(instructorEntities);
        customerRepository.save(customerEntity);
    }

    private void checkInstructorCredentials(String userName, String password) {
        if (!authenticationService.matchInstructorCredentials(userName, password)) {
            throw new SecurityException("authentication failed");
        }
    }
}

