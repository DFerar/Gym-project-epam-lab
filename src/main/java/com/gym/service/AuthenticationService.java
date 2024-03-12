package com.gym.service;

import com.gym.repository.CustomerRepository;
import com.gym.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final CustomerRepository customerRepository;
    private final InstructorRepository instructorRepository;

    public boolean matchCustomerCredentials(String username, String password) {
        return customerRepository.existsByGymUserEntity_UserNameAndGymUserEntity_Password(username, password);
    }

    public boolean matchInstructorCredentials(String username, String password) {
        return instructorRepository.existsByGymUserEntity_UserNameAndGymUserEntity_Password(username, password);
    }
}
