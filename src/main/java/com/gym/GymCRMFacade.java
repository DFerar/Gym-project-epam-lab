package com.gym;

import com.gym.dto.CustomerDto;
import com.gym.entity.CustomerEntity;
import com.gym.entity.InstructorEntity;
import com.gym.entity.TrainingEntity;
import com.gym.service.CustomerService;
import com.gym.service.InstructorService;
import com.gym.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GymCRMFacade {
    private final CustomerService customerService;
    private final TrainingService trainingService;
    private final InstructorService instructorService;

    public CustomerDto createCustomer(CustomerDto customer) {
        return customerService.createCustomer(customer);
    }

    public void deleteCustomer(Integer customerId) {
        customerService.deleteCustomer(customerId);
    }

    public CustomerEntity updateCustomer(CustomerEntity newData) {
        return customerService.updateCustomer(newData);
    }

    public CustomerDto getCustomerById(Integer customerId) {
        return customerService.getCustomerById(customerId);
    }

    public InstructorEntity createInstructor(InstructorEntity instructor) {
        return instructorService.createInstructor(instructor);
    }

    public void deleteInstructor(Integer instructorId) {
        instructorService.deleteInstructor(instructorId);
    }

    public InstructorEntity updateInstructor(InstructorEntity newData) {
        return instructorService.updateInstructor(newData);
    }

    public InstructorEntity getInstructorById(Integer instructorId) {
        return instructorService.getInstructorById(instructorId);
    }

    public TrainingEntity getTrainingById(Integer trainingId) {
        return trainingService.getTrainingById(trainingId);
    }

    public TrainingEntity createTraining(TrainingEntity training) {
        return trainingService.createTraining(training);
    }
}
