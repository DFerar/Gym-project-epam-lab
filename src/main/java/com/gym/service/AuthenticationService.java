package com.gym.service;

import com.gym.entity.GymUserEntity;
import com.gym.exceptionHandler.LoginException;
import com.gym.repository.CustomerRepository;
import com.gym.repository.GymUserRepository;
import com.gym.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final CustomerRepository customerRepository;
    private final InstructorRepository instructorRepository;
    private final GymUserRepository gymUserRepository;

    public void matchCustomerCredentials(String username, String password) {
        if (!customerRepository.existsByGymUserEntityUserNameAndGymUserEntityPassword(username, password)) {
            throw new LoginException("Wrong credentials");
        }
    }

    public void matchInstructorCredentials(String username, String password) {
        if (!instructorRepository.existsByGymUserEntityUserNameAndGymUserEntityPassword(username, password)) {
            throw new LoginException("Wrong credentials");
        }
    }

    public void changeUsersPassword(String loginUsername, String loginPassword, String newPassword) {
        if (!gymUserRepository.existsByUserNameAndPassword(loginUsername, loginPassword)) {
            throw new LoginException("Wrong credentials");
        } else {
            GymUserEntity gymUserEntity = gymUserRepository.findByUserName(loginUsername);
            gymUserEntity.setPassword(newPassword);
            gymUserRepository.save(gymUserEntity);
        }
    }

    public void matchCredentials(String username, String password) {
        if (!gymUserRepository.existsByUserNameAndPassword(username, password)) {
            throw new SecurityException("Wrong credentials");
        }
    }
}
