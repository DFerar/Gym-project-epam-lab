package com.gym;

import com.gym.customer.Customer;
import com.gym.customer.CustomerService;
import com.gym.instructor.InstructorService;
import com.gym.training.Training;
import com.gym.training.TrainingService;
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

    public com.gym.instructor.Instructor createInstructor(com.gym.instructor.Instructor instructor) {
        return instructorService.createInstructor(instructor);
    }

    public void deleteInstructor(Integer instructorId) {
        instructorService.deleteInstructor(instructorId);
    }

    public com.gym.instructor.Instructor updateInstructor(com.gym.instructor.Instructor newData) {
        return instructorService.updateInstructor(newData);
    }

    public com.gym.instructor.Instructor getInstructorById(Integer instructorId) {
        return instructorService.getInstructorById(instructorId);
    }

    public Training getTrainingById(Integer trainingId) {
        return trainingService.getTrainingById(trainingId);
    }

    public Training createTraining(Training training) {
        return trainingService.createTraining(training);
    }
}
