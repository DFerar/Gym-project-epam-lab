package com.gym.mapper;

import com.gym.entity.TokenEntity;
import org.springframework.stereotype.Component;

@Component
public class TokenMapper {
    /**
     * Accepts a token and a username, maps them into a TokenEntity.
     *
     * @param token    - provided token that needs to be mapped into a TokenEntity
     * @param username - the username of the user
     * @return TokenEntity - returns a Valid TokenEntity with provided token and username
     */
    public TokenEntity mapTokenToTokenEntity(String token, String username) {
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setToken(token);
        tokenEntity.setIsValid(true);
        tokenEntity.setUsername(username);
        return tokenEntity;
    }
}
