package com.gym.security.jwt;

import com.gym.properties.JwtProperties;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;

    /**
     * Generate JWT token for authenticated user.
     *
     * @param authentication - the authentication object containing the user's details
     * @return token - a JWT token
     * @throws JOSEException - if the application fails to generate the token
     */
    public String generateToken(Authentication authentication) throws JOSEException {
        String username = authentication.getName();
        Instant currentDate = Instant.now();
        Instant expireDate = currentDate.plus(jwtProperties.getExpiration(), ChronoUnit.MILLIS);

        JWSSigner signer = new MACSigner(jwtProperties.getKey().getBytes(StandardCharsets.UTF_8));

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
            .subject(username)
            .issueTime(Date.from(currentDate))
            .expirationTime(Date.from(expireDate))
            .build();

        SignedJWT signedJwt = new SignedJWT(new JWSHeader(JWSAlgorithm.HS384), claimsSet);

        signedJwt.sign(signer);

        return signedJwt.serialize();
    }
}
