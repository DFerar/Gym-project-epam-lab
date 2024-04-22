package com.gym.service;

import com.gym.entity.GymUserEntity;
import com.gym.exception.LoginException;
import com.gym.exception.UserNotFoundException;
import com.gym.repository.GymUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordChangeService {
    private final GymUserRepository gymUserRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Changes the existing password of a user to a new one. If credentials not valid, throws LoginException.
     *
     * @param loginUsername The current username of the user.
     * @param loginPassword The current password of the user.
     * @param newPassword   The new password.
     * @throws LoginException if the current credentials do not match a user in the database.
     */
    public void changeUsersPassword(String loginUsername, String loginPassword, String newPassword) {
        GymUserEntity gymUserEntity = gymUserRepository.findByUserName(loginUsername);
        if (gymUserEntity == null) {
            throw new UserNotFoundException("User not found");
        }
        if (!passwordEncoder.matches(loginPassword, gymUserEntity.getPassword())) {
            throw new LoginException("Wrong password");
        }
        gymUserEntity.setPassword(passwordEncoder.encode(newPassword));
        gymUserRepository.save(gymUserEntity);
    }
}
