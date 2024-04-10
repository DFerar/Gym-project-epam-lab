package com.gym.service;


import com.gym.entity.GymUserEntity;
import com.gym.exception.UserNotFoundException;
import com.gym.repository.GymUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GymUserService {
    private final GymUserRepository gymUserRepository;
    private final PasswordEncoder passwordEncoder;

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
     * This method is used to create a GymUserEntity. It verifies if a user with the same userName exists
     * in the database. If there is no existing user with the same username, it sets the user index to 1 and
     * creates a user. Otherwise, it increments the index by 1 and creates a user with the new index.
     *
     * @param gymUserEntityFromDto Object containing the details of the gym user to be created
     * @return gymUserEntityFromDto object after saving it in the database
     */
    @Transactional
    public GymUserEntity createUser(GymUserEntity gymUserEntityFromDto) {
        String baseUserName = gymUserEntityFromDto.getFirstName() + "." + gymUserEntityFromDto.getLastName();
        Boolean userNameExists = gymUserRepository.existsByUserName(baseUserName);
        if (!userNameExists) {
            gymUserEntityFromDto.setUserIndex(1);
            gymUserEntityFromDto.setUserName(baseUserName);
        } else {
            int maxIndex = gymUserRepository.findMaxUserIndexByFirstNameAndLastName(gymUserEntityFromDto.getFirstName(),
                gymUserEntityFromDto.getLastName());
            int suffix = maxIndex + 1;
            gymUserEntityFromDto.setUserIndex(suffix);
            String userName = baseUserName + suffix;
            gymUserEntityFromDto.setUserName(userName);
        }
        gymUserEntityFromDto.setPassword(passwordEncoder.encode(gymUserEntityFromDto.getPassword()));
        return gymUserRepository.save(gymUserEntityFromDto);
    }
}
