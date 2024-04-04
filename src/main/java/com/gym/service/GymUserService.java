package com.gym.service;


import com.gym.entity.GymUserEntity;
import com.gym.exception.UserNotFoundException;
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

    /**
     * Updates a GymUserEntity's fields and saves it in the database.
     *
     * @param userEntity GymUserEntity object containing the updated details.
     * @return GymUserEntity representing the updated user.
     * @throws UserNotFoundException When a user with the given username is not found.
     */
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

    /**
     * Generates a unique username based on a provided first and last name.
     *
     * @param firstName The user's first name.
     * @param lastName  The user's last name.
     * @return A unique username for the user.
     */
    @Transactional
    public String generateUniqueUserName(String firstName, String lastName) {
        String baseUserName = firstName + "." + lastName;
        Boolean userNameExist = gymUserRepository.existsByUserName(baseUserName);
        if (userNameExist) {
            int nextUserSuffix =
                gymUserRepository.findGymUserEntitiesByFirstNameAndLastName(firstName, lastName).size() + 1;
            return baseUserName + nextUserSuffix;
        } else {
            return baseUserName;
        }
    }
}
