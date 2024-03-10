package com.gym;

import com.gym.dto.CustomerDto;
import com.gym.dto.InstructorDto;
import com.gym.dto.TrainingDto;
import com.gym.service.AuthenticationService;
import com.gym.service.CustomerService;
import com.gym.service.InstructorService;
import com.gym.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GymCRMFacade {
    private final CustomerService customerService;
    private final TrainingService trainingService;
    private final InstructorService instructorService;
    private final AuthenticationService authenticationService;

    public boolean matchCustomerCredentials(String username, String password) {
        return authenticationService.matchCustomerCredentials(username, password);
    }

    public boolean matchInstructorCredentials(String username, String password) {
        return authenticationService.matchInstructorCredentials(username, password);
    }

    public CustomerDto createCustomer(CustomerDto customer) {
        return customerService.createCustomer(customer);
    }

    public CustomerDto getCustomerByUserName(String loginUserName, String loginPassword, String userName) {
        return customerService.getCustomerByUserName(loginUserName, loginPassword, userName);
    }

    public void changeCustomerPassword(String loginUserName, String loginPassword, Integer customerId,
                                       String newPassword) {
        customerService.changeCustomerPassword(loginUserName, loginPassword, customerId, newPassword);
    }

    public void changeCustomerActivity(String loginUserName, String loginPassword, Integer userId,
                                       boolean newActivity) {
        customerService.changeCustomersActivity(loginUserName, loginPassword, userId, newActivity);
    }

    public void deleteCustomerByUserName(String loginUserName, String loginPassword, String userName) {
        customerService.deleteCustomerByUserName(loginUserName, loginPassword, userName);
    }

    public CustomerDto updateCustomer(String loginUserName, String loginPassword, CustomerDto newData) {
        return customerService.updateCustomer(loginUserName, loginPassword, newData);
    }

    public CustomerDto getCustomerById(String loginUserName, String loginPassword, Integer customerId) {
        return customerService.getCustomerById(loginUserName, loginPassword, customerId);
    }

    public List<TrainingDto> getCustomerTrainings(String loginUserName, String loginPassword,
                                                  String customerName, Date fromDate, Date toDate,
                                                  String instructorName,
                                                  String trainingTypeName) {
        return customerService.getCustomerTrainings(loginUserName, loginPassword,
                customerName, fromDate, toDate, instructorName, trainingTypeName);
    }

    public InstructorDto createInstructor(InstructorDto instructorDto) {
        return instructorService.createInstructor(instructorDto);
    }

    public InstructorDto getInstructorByUserName(String loginUserName, String loginPassword, String userName) {
        return instructorService.getInstructorByUsername(loginUserName, loginPassword, userName);
    }

    public void changeInstructorPassword(String loginUserName, String loginPassword, Integer userId,
                                         String newPassword) {
        instructorService.changeInstructorPassword(loginUserName, loginPassword, userId, newPassword);
    }

    public void changeInstructorActivity(String loginUserName, String loginPassword, Integer userId,
                                         boolean newActivity) {
        instructorService.changeInstructorActivity(loginUserName, loginPassword, userId, newActivity);
    }

    public List<TrainingDto> getInstructorTrainings(String loginUserName, String loginPassword, String instructorName,
                                                    Date fromDate,
                                                    Date toDate, String customerName) {
        return instructorService.getInstructorTrainings(loginUserName, loginPassword, instructorName, fromDate, toDate,
                customerName);
    }

    public List<InstructorDto> getInstructorsNotAssignedToCustomerByCustomerUserName(String loginUserName,
                                                                                     String loginPassword,
                                                                                     String customerUsername) {
        return instructorService.getInstructorsNotAssignedToCustomerByCustomerUserName(loginUserName, loginPassword,
                customerUsername);
    }


    public InstructorDto updateInstructor(String loginUserName, String loginPassword, InstructorDto newData) {
        return instructorService.updateInstructor(loginUserName, loginPassword, newData);
    }

    public InstructorDto getInstructorById(String loginUserName, String loginPassword, Integer instructorId) {
        return instructorService.getInstructorById(loginUserName, loginPassword, instructorId);
    }

    public TrainingDto createTraining(String loginUserName, String loginPassword, TrainingDto training) {
        return trainingService.createTraining(loginUserName, loginPassword, training);
    }
}
