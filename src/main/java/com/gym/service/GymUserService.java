package com.gym.service;


import com.gym.repository.GymUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GymUserService {
    private final GymUserRepository gymUserRepository;

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
        return (maxUserId != null) ? maxUserId + 1: 1;
    }
}
