package com.gym;

import com.gym.dto.CustomerDto;
import com.gym.dto.InstructorDto;
import com.gym.dto.TrainingDto;
import com.gym.utils.Utils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.Date;

import static com.gym.entity.TrainingType.CARDIO;
import static com.gym.entity.TrainingType.TRX;

public class GymCRMApp {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            GymCRMFacade gymCRMFacade = context.getBean(GymCRMFacade.class);
//            login as admin
            String loginUserName = "admin.admin";
            String loginPassword = "admin";
//            create commands
            CustomerDto customerDto1 = new CustomerDto(null, Date.valueOf("1997-01-01"), "1st street",
                    null, "Denis", "Ferari", null, true);
            CustomerDto repeatedCustomer = new CustomerDto(null, Date.valueOf("1998-01-01"), "1st street",
                    null, "Denis", "Ferari", null, true);
            CustomerDto createdCustomer1 = gymCRMFacade.createCustomer(customerDto1);
            System.out.println(createdCustomer1);

            InstructorDto instructorDto1 = new InstructorDto(null, CARDIO, null, "John",
                    "Smith", null, false);
            InstructorDto savedInstructor1 = gymCRMFacade.createInstructor(instructorDto1);
            System.out.println(savedInstructor1);

            TrainingDto trainingDto1 = new TrainingDto(null, 1L, 1L, "1st training",
                    1L, Date.valueOf("2024-04-01"), 1);
            TrainingDto savedTraining1 = gymCRMFacade.createTraining(loginUserName,
                    loginPassword, trainingDto1);
            System.out.println(savedTraining1);
//            update commands
            CustomerDto newData = new CustomerDto(1L, Date.valueOf("1997-01-02"), "1st new street",
                    1L, "Denis", "Ferar", null, true);
            CustomerDto customerDto = gymCRMFacade.updateCustomer(loginUserName, loginPassword, newData);
            System.out.println(customerDto);

            InstructorDto newDataForInstructor = new InstructorDto(1L, TRX, 2L, "John_new",
                    "Smith", null, false);
            InstructorDto instructorDto = gymCRMFacade.updateInstructor(loginUserName, loginPassword, newDataForInstructor);
            System.out.println(instructorDto);
            gymCRMFacade.changeInstructorActivity(loginUserName, loginPassword, 2L, true);
            gymCRMFacade.changeInstructorPassword(loginUserName, loginPassword, 2L, Utils.generatePassword());
            gymCRMFacade.changeCustomerPassword(loginUserName, loginPassword, 1L, Utils.generatePassword());
            gymCRMFacade.changeCustomerActivity(loginUserName, loginPassword, 1L, false);
//            get commands
            CustomerDto returnedCustomer = gymCRMFacade.getCustomerByUserName(loginUserName, loginPassword, "Denis.Ferar");
            System.out.println(returnedCustomer);
            InstructorDto returnedInstructor = gymCRMFacade.getInstructorByUserName(loginUserName, loginPassword, "John_new.Smith");
            System.out.println(returnedInstructor);
            TrainingDto trainingDto2 = new TrainingDto(null, 1L, 1L, "1st training",
                    1L, Date.valueOf("2024-01-03"), 3);
            TrainingDto savedTraining2 = gymCRMFacade.createTraining(loginUserName, loginPassword, trainingDto2);
            System.out.println(savedTraining2);
            var list = gymCRMFacade.getInstructorsNotAssignedToCustomerByCustomerUserName(
                    loginUserName, loginPassword, "Denis.Ferar");
            System.out.println(list);
            System.out.println(list.size() == 3);
            var list1 = gymCRMFacade.getInstructorTrainings(
                    loginUserName, loginPassword, "John_new.Smith", null, null,
                    "Denis.Ferar");
            System.out.println(list1);
            System.out.println(list1.size() == 1);
//            delete commands
            //gymCRMFacade.deleteCustomerByUserName(loginUserName, loginPassword, "Denis.Ferar");
        }
    }
}
