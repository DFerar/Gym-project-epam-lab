package com.gym.security.authentication;

import com.gym.entity.BlockedUserEntity;
import com.gym.repository.security.BlockedUserRepository;
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
    private final BlockedUserRepository blockedUserRepository;
    private final ConcurrentHashMap<String, Integer> attemptsCache = new ConcurrentHashMap<>();

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
        BlockedUserEntity blockedUserEntity = blockedUserRepository.findByUsername(username);
        return blockedUserEntity != null;
    }

    /**
     * Removes expired blocks from the blocked user repository every 60 seconds.
     */
    @Transactional
    @Scheduled(fixedRate = 60, timeUnit = TimeUnit.SECONDS)
    public void removeExpiredBlocks() {
        LocalDateTime now = LocalDateTime.now();
        blockedUserRepository.deleteAllByTimestampOfUnblockingBefore(now);
    }

    /**
     * Blocks a user by adding the user to the blocked user repository.
     *
     * @param username - the username to be blocked
     */
    private void blockUser(String username) {
        LocalDateTime blockTime = LocalDateTime.now();
        LocalDateTime unblockTime = LocalDateTime.now().plusMinutes(BLOCK_DURATION_MINUTES);
        BlockedUserEntity blockedUserEntity = new BlockedUserEntity();
        blockedUserEntity.setUsername(username);
        blockedUserEntity.setTimestampOfBlocking(blockTime);
        blockedUserEntity.setTimestampOfUnblocking(unblockTime);
        blockedUserRepository.save(blockedUserEntity);
        attemptsCache.remove(username);
    }
}
