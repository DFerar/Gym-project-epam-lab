package com.gym.repository.security;

import com.gym.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    TokenEntity findByToken(String token);

    TokenEntity findByUsername(String username);
}
