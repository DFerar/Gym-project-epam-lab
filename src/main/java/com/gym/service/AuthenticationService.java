package com.gym.service;

import com.gym.entity.GymUserEntity;
import com.gym.exception.LoginException;
import com.gym.repository.CustomerRepository;
import com.gym.repository.GymUserRepository;
import com.gym.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final CustomerRepository customerRepository;
    private final InstructorRepository instructorRepository;
    private final GymUserRepository gymUserRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Validates customer credentials. If credentials not valid, throws LoginException.
     *
     * @param username The username of the customer.
     * @param password The password of the customer.
     * @throws LoginException if the credentials do not match a customer user in the database.
     */
    public void matchCustomerCredentials(String username, String password) {
        if (!customerRepository.existsByGymUserEntityUserNameAndGymUserEntityPassword(username, password)) {
            throw new LoginException("Wrong credentials");
        }
    }

    /**
     * Validates instructor credentials. If credentials not valid, throws LoginException.
     *
     * @param username The username of the instructor.
     * @param password The password of the instructor.
     * @throws LoginException if the credentials do not match an instructor user in the database.
     */
    public void matchInstructorCredentials(String username, String password) {
        if (!instructorRepository.existsByGymUserEntityUserNameAndGymUserEntityPassword(username, password)) {
            throw new LoginException("Wrong credentials");
        }
    }

    /**
     * Changes the existing password of a user to a new one. If credentials not valid, throws LoginException.
     *
     * @param loginUsername The current username of the user.
     * @param loginPassword The current password of the user.
     * @param newPassword   The new password.
     * @throws LoginException if the current credentials do not match a user in the database.
     */
    public void changeUsersPassword(String loginUsername, String loginPassword, String newPassword) {
        if (!gymUserRepository.existsByUserNameAndPassword(loginUsername, loginPassword)) {
            throw new LoginException("Wrong credentials");
        } else {
            GymUserEntity gymUserEntity = gymUserRepository.findByUserName(loginUsername);
            gymUserEntity.setPassword(passwordEncoder.encode(newPassword));
            gymUserRepository.save(gymUserEntity);
        }
    }

    /**
     * Validates user credentials. If credentials not valid, throws SecurityException.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @throws LoginException if the credentials do not match a user in the database.
     */
    public void matchCredentials(String username, String password) {
        if (!gymUserRepository.existsByUserNameAndPassword(username, password)) {
            throw new LoginException("Wrong credentials");
        }
    }
}
