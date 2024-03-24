package com.gym.service;


import com.gym.entity.GymUserEntity;
import com.gym.repository.GymUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class GymUserService {
    private final GymUserRepository gymUserRepository;

    @Transactional
    public GymUserEntity updateUser(GymUserEntity userEntity) {
        GymUserEntity gymUserEntity = gymUserRepository.findByUserName(userEntity.getUserName());
        if (gymUserEntity == null) {
            throw new NoSuchElementException("User not found");
        }
        gymUserEntity.setFirstName(userEntity.getFirstName());
        gymUserEntity.setLastName(userEntity.getLastName());
        gymUserEntity.setIsActive(userEntity.getIsActive());
        return gymUserRepository.save(gymUserEntity);
    }

    public String generateUniqueUserName(String firstName, String lastName) {
        String baseUserName = firstName + "." + lastName;
        Boolean userNameExist = gymUserRepository.existsByUserName(baseUserName);
        if (userNameExist) {
            long nextUserId = getNextAvailableUserId() + 1L;
            return baseUserName + Long.toString(nextUserId);
        } else {
            return baseUserName;
        }
    }

    private long getNextAvailableUserId() {
        return gymUserRepository.findMaxUserId();
    }
}
