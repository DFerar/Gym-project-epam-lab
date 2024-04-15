package com.gym.security.authentication;

import com.gym.entity.BlockedUserEntity;
import com.gym.repository.security.BlockedUserRepository;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoggingAttemptsService {
    private static final int MAX_ATTEMPTS = 3;
    private static final int BLOCK_DURATION_MINUTES = 5;
    private final BlockedUserRepository blockedUserRepository;
    private final ConcurrentHashMap<String, Integer> attemptsCache = new ConcurrentHashMap<>();


    public void addUserToLoginAttemptsCache(String username) {
        int attempts = attemptsCache.getOrDefault(username, 0);
        attempts++;
        attemptsCache.put(username, attempts);
        if (attempts >= MAX_ATTEMPTS) {
            blockUser(username);
        }
    }

    public Boolean isBlocked(String username) {
        BlockedUserEntity blockedUserEntity = blockedUserRepository.findByUsername(username);
        return blockedUserEntity != null;
    }

    /*@Scheduled(fixedRate = 60, timeUnit = TimeUnit.SECONDS) //TODO: timechached(lib)
    public void removeExpiredBlocks() {
        LocalDateTime now = LocalDateTime.now();
        blockedUserRepository.deleteAllByTimestampOfUnblockingBefore(now);
    }*/

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
