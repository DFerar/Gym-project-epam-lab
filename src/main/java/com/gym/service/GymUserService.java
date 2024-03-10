package com.gym.service;


import com.gym.entity.GymUserEntity;
import com.gym.repository.GymUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class GymUserService {
    private final GymUserRepository gymUserRepository;

    public GymUserEntity updateUser(Integer userId, String firstName, String lastName, Boolean isActive) {
        GymUserEntity gymUserEntity = gymUserRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        String newUserName = generateUniqueUserName(firstName, lastName);
        gymUserEntity.setFirstName(firstName);
        gymUserEntity.setLastName(lastName);
        gymUserEntity.setUserName(newUserName);
        gymUserEntity.setIsActive(isActive);
        return gymUserRepository.save(gymUserEntity);
    }

    public String generateUniqueUserName(String firstName, String lastName) {
        String baseUserName = firstName + "." + lastName;
        Boolean userNameExist = gymUserRepository.existsByUserName(baseUserName);
        if (userNameExist) {
            Integer nextUserId = getNextAvailableUserId();
            return baseUserName + nextUserId;
        } else {
            return baseUserName;
        }
    }

    private Integer getNextAvailableUserId() {
        Integer maxUserId = gymUserRepository.findMaxUserId();
        return (maxUserId != null) ? maxUserId + 1 : 1;
    }
}
