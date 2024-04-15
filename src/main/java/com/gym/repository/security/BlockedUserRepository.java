package com.gym.repository.security;

import com.gym.entity.BlockedUserEntity;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockedUserRepository extends JpaRepository<BlockedUserEntity, Long> {
    BlockedUserEntity findByUsername(String username);

    void deleteAllByTimestampOfUnblockingBefore(LocalDateTime time);
}
