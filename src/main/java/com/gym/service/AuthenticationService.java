package com.gym.service;

import com.gym.entity.GymUserEntity;
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

    public boolean matchCustomerCredentials(String username, String password) {
        return customerRepository.existsByGymUserEntityUserNameAndGymUserEntityPassword(username, password);
    }

    public boolean matchInstructorCredentials(String username, String password) {
        return instructorRepository.existsByGymUserEntityUserNameAndGymUserEntityPassword(username, password);
    }

    public void changeUsersPassword(String loginUsername, String loginPassword, String newPassword) {
        if (matchCredentials(loginUsername, loginPassword)) {
            GymUserEntity gymUserEntity = gymUserRepository.findByUserName(loginUsername);
            gymUserEntity.setPassword(newPassword);
            gymUserRepository.save(gymUserEntity);
        } else {
            throw new SecurityException("Incorrect credentials");
        }
    }

    public boolean matchCredentials(String username, String password) {
        GymUserEntity gymUserEntity = gymUserRepository.findByUserName(username);
        return gymUserEntity != null && gymUserEntity.getPassword().equals(password);
    }
}
