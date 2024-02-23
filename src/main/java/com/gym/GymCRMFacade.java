package com.gym;

import com.gym.entities.Customer;
import com.gym.entities.Instructor;
import com.gym.entities.Training;
import com.gym.services.CustomerService;
import com.gym.services.InstructorService;
import com.gym.services.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GymCRMFacade {
    private final CustomerService customerService;
    private final TrainingService trainingService;
    private final InstructorService instructorService;

    public Customer createCustomer(Customer customer) {
        return customerService.createCustomer(customer);
    }

    public void deleteCustomer(Integer customerId) {
        customerService.deleteCustomer(customerId);
    }

    public Customer updateCustomer(Customer newData) {
        return customerService.updateCustomer(newData);
    }

    public Customer getCustomerById(Integer customerId) {
        return customerService.getCustomerById(customerId);
    }

    public Instructor createInstructor(Instructor instructor) {
        return instructorService.createInstructor(instructor);
    }

    public void deleteInstructor(Integer instructorId) {
        instructorService.deleteInstructor(instructorId);
    }

    public Instructor updateInstructor(Instructor newData) {
        return instructorService.updateInstructor(newData);
    }

    public Instructor getInstructorById(Integer instructorId) {
        return instructorService.getInstructorById(instructorId);
    }

    public Training getTrainingById(Integer trainingId) {
        return trainingService.getTrainingById(trainingId);
    }

    public Training createTraining(Training training) {
        return trainingService.createTraining(training);
    }
}
