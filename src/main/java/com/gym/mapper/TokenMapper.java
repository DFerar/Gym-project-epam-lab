package com.gym.mapper;

import com.gym.entity.TokenEntity;
import org.springframework.stereotype.Component;

@Component
public class TokenMapper {
    public TokenEntity mapTokenToTokenEntity(String token, String username) {
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setToken(token);
        tokenEntity.setIsValid(true);
        tokenEntity.setUsername(username);
        return tokenEntity;
    }
}
