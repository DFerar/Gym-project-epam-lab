package com.gym;

import com.gym.entity.CustomerEntity;
import com.gym.entity.InstructorEntity;
import com.gym.entity.TrainingEntity;
import com.gym.entity.TrainingType;
import com.gym.storage.StorageInitializer;
import java.time.LocalDate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class GymCRMApp {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            context.getBean(StorageInitializer.class);
            GymCRMFacade gymCRMFacade = context.getBean(GymCRMFacade.class);
            //commands

            //getCustomer
            gymCRMFacade.getCustomerById(1);
            //getInstructor
            gymCRMFacade.getInstructorById(1);
            //getTraining
            gymCRMFacade.getTrainingById(1);
            //deleteCustomer
//            gymCRMFacade.deleteCustomer(3);
            //createCustomer
            CustomerEntity newCustomer = new CustomerEntity();
            newCustomer.setFirstName("Kek");
            newCustomer.setLastName("Lol");
            newCustomer.setAddress("lol_str");
            newCustomer.setDateOfBirth(LocalDate.parse("1990-01-01"));
            newCustomer.setIsActive(true);
            gymCRMFacade.createCustomer(newCustomer);
            gymCRMFacade.getCustomerById(2);

            //updateCustomer
            CustomerEntity updatedCustomer = new CustomerEntity();
            updatedCustomer.setUserId(1);
            updatedCustomer.setFirstName("Kek1");
            updatedCustomer.setLastName("Lol1");
            updatedCustomer.setAddress("lol_str1");
            updatedCustomer.setDateOfBirth(LocalDate.parse("1990-01-01"));
            updatedCustomer.setIsActive(false);
            gymCRMFacade.updateCustomer(updatedCustomer);
            //createInstructor
            InstructorEntity newInstructor = new InstructorEntity();
            newInstructor.setFirstName("trainer");
            newInstructor.setLastName("kek");
            newInstructor.setSpecialization("keklol");
            newInstructor.setIsActive(true);
            gymCRMFacade.createInstructor(newInstructor);
            //deleteInstructor
//            gymCRMFacade.deleteInstructor(15);
            gymCRMFacade.getInstructorById(5);
            //updateTrainer
            InstructorEntity updateData = new InstructorEntity();
            updateData.setUserId(4);
            updateData.setFirstName("trainer4");
            updateData.setLastName("kek4");
            updateData.setSpecialization("keklol4");
            updateData.setIsActive(false);
            gymCRMFacade.updateInstructor(updateData);
            //create Training
            TrainingEntity newTraining = new TrainingEntity();
            newTraining.setTrainingDate(LocalDate.parse("1990-01-01"));
            newTraining.setTrainingDuration("2 hours");
            newTraining.setTrainingName("kek");
            newTraining.setTrainingType(TrainingType.CARDIO);
            newTraining.setCustomerId(4);
            newTraining.setInstructorId(1);
            gymCRMFacade.createTraining(newTraining);
            //get Trainnig
            gymCRMFacade.getTrainingById(2);
        }
    }
}
