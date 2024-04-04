package com.gym.service;


import com.gym.entity.GymUserEntity;
import com.gym.exceptionHandler.UserNotFoundException;
import com.gym.repository.GymUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GymUserService {
    private final GymUserRepository gymUserRepository;

    @Transactional
    public GymUserEntity updateUser(GymUserEntity userEntity) {
        GymUserEntity gymUserEntity = gymUserRepository.findByUserName(userEntity.getUserName());
        if (gymUserEntity == null) {
            throw new UserNotFoundException("User not found");
        }
        gymUserEntity.setFirstName(userEntity.getFirstName());
        gymUserEntity.setLastName(userEntity.getLastName());
        gymUserEntity.setIsActive(userEntity.getIsActive());
        return gymUserRepository.save(gymUserEntity);
    }

    @Transactional
    public String generateUniqueUserName(String firstName, String lastName) {
        String baseUserName = firstName + "." + lastName;
        Boolean userNameExist = gymUserRepository.existsByUserName(baseUserName);
        if (userNameExist) {
            int nextUserSuffix = gymUserRepository.findGymUserEntitiesByFirstNameAndLastName(firstName, lastName).size();
            return baseUserName + nextUserSuffix;
        } else {
            return baseUserName;
        }
    }
}
