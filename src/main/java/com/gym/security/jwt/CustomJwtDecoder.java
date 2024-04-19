package com.gym.security.jwt;

import com.gym.entity.TokenEntity;
import com.gym.repository.security.TokenRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

@Component
public class CustomJwtDecoder implements JwtDecoder {
    private final JwtDecoder jwtDecoder;
    private final TokenRepository tokenRepository;

    public CustomJwtDecoder(@Lazy JwtDecoder jwtDecoder, TokenRepository tokenRepository) {
        this.jwtDecoder = jwtDecoder;
        this.tokenRepository = tokenRepository;
    }

    /**
     * The method decodes the Jwt by checking first if its valid and exists in the database.
     *
     * @param token - The token to be decoded.
     * @return Jwt - The decoded Jwt.
     * @throws JwtException - When the Jwt is invalid.
     */
    @Override
    public Jwt decode(String token) throws JwtException {
        TokenEntity tokenFromDatabase = tokenRepository.findByToken(token);
        if (tokenFromDatabase == null) {
            throw new BadJwtException("invalid token");
        }
        if (!tokenFromDatabase.getIsValid()) {
            throw new BadJwtException("invalid token");
        }
        return jwtDecoder.decode(token);
    }
}

