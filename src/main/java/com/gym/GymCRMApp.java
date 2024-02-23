package com.gym;

import com.gym.entities.Customer;
import com.gym.entities.Instructor;
import com.gym.entities.Training;
import com.gym.entities.TrainingType;
import com.gym.storage.StorageInitializer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class GymCRMApp {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            context.getBean(StorageInitializer.class);
            GymCRMFacade gymCRMFacade = context.getBean(GymCRMFacade.class);
            //commands

            //getCustomer
            gymCRMFacade.getCustomerById(2);
            //getInstructor
            gymCRMFacade.getInstructorById(1);
            //getTraining
            gymCRMFacade.getTrainingById(4);
            //deleteCustomer
            //gymCRMFacade.deleteCustomer(10);
            //createCustomer
            Customer newCustomer = new Customer();
            newCustomer.setFirstName("Kek");
            newCustomer.setLastName("Lol");
            newCustomer.setAddress("lol_str");
            newCustomer.setDateOfBirth("123213");
            newCustomer.setIsActive(true);
            gymCRMFacade.createCustomer(newCustomer);
            gymCRMFacade.getCustomerById(6);

            //updateCustomer
            Customer updatedCustomer = new Customer();
            updatedCustomer.setUserId(1);
            updatedCustomer.setFirstName("Kek1");
            updatedCustomer.setLastName("Lol1");
            updatedCustomer.setAddress("lol_str1");
            updatedCustomer.setDateOfBirth("1232131");
            updatedCustomer.setIsActive(false);
            gymCRMFacade.updateCustomer(updatedCustomer);
            //createInstructor
            Instructor newInstructor = new Instructor();
            newInstructor.setFirstName("trainer");
            newInstructor.setLastName("kek");
            newInstructor.setSpecialization("keklol");
            newInstructor.setIsActive(true);
            gymCRMFacade.createInstructor(newInstructor);
            //deleteInstructor
            //gymCRMFacade.deleteInstructor(5);
            //gymCRMFacade.getInstructorById(5);
            //updateTrainer
            Instructor updateData = new Instructor();
            updateData.setUserId(4);
            updateData.setFirstName("trainer4");
            updateData.setLastName("kek4");
            updateData.setSpecialization("keklol4");
            updateData.setIsActive(false);
            gymCRMFacade.updateInstructor(updateData);
            //create Training
            Training newTraining = new Training();
            newTraining.setTrainingDate("213123123");
            newTraining.setTrainingDuration("2 hours");
            newTraining.setTrainingName("kek");
            newTraining.setTrainingType(TrainingType.CARDIO);
            newTraining.setCustomerId(4);
            newTraining.setInstructorId(1);
            gymCRMFacade.createTraining(newTraining);
        }
    }
}
