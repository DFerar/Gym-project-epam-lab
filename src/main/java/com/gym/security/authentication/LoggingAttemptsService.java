package com.gym.security.authentication;

import com.gym.entity.GymUserEntity;
import com.gym.entity.TokenEntity;
import com.gym.exception.UserNotFoundException;
import com.gym.repository.GymUserRepository;
import com.gym.repository.security.TokenRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoggingAttemptsService {
    private static final int MAX_ATTEMPTS = 3;
    private static final int BLOCK_DURATION_MINUTES = 5;
    private final ConcurrentHashMap<String, Integer> attemptsCache = new ConcurrentHashMap<>();
    private final TokenRepository tokenRepository;
    private final GymUserRepository gymUserRepository;

    /**
     * Adds a user to the login attempts cache. If user exceeds the max attempts, blocks the user.
     *
     * @param username - the username that attempted to log in
     */
    public void addUserToLoginAttemptsCache(String username) {
        int attempts = attemptsCache.getOrDefault(username, 0);
        attempts++;
        attemptsCache.put(username, attempts);
        if (attempts >= MAX_ATTEMPTS) {
            blockUser(username);
        }
    }

    /**
     * Checks if a user is blocked.
     *
     * @param username - the username to be checked.
     * @return Boolean - returns true if the user is blocked, false otherwise.
     */
    public Boolean isBlocked(String username) {
        LocalDateTime now = LocalDateTime.now();
        GymUserEntity blockedUserEntity = gymUserRepository.findByUserName(username);
        if (blockedUserEntity == null) {
            throw new UserNotFoundException("User not found");
        }
        return blockedUserEntity.getTimeOfBlocking() != null && !now.isAfter(blockedUserEntity.getTimeOfBlocking()
            .plusMinutes(BLOCK_DURATION_MINUTES));
    }

    /**
     * Deletes all tokens in database every two days.
     */
    @Transactional
    @Scheduled(fixedRate = 2, timeUnit = TimeUnit.DAYS)
    public void deleteTokens() {
        tokenRepository.deleteAll();
    }

    /**
     * Blocks a user by adding the user to the blocked user repository.
     *
     * @param username - the username to be blocked
     */
    private void blockUser(String username) {
        LocalDateTime blockTime = LocalDateTime.now();
        GymUserEntity gymUserEntity = gymUserRepository.findByUserName(username);
        if (gymUserEntity == null) {
            throw new UserNotFoundException("User not found");
        }
        gymUserEntity.setTimeOfBlocking(blockTime);
        gymUserRepository.save(gymUserEntity);
        TokenEntity token = tokenRepository.findByUsername(username);
        if (token == null) {
            throw new UserNotFoundException("User not found");
        }
        token.setIsValid(false);
        tokenRepository.save(token);
        attemptsCache.remove(username);
    }
}
