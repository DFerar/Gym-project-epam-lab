package com.gym.security.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    @Value("${token.signing.key}")
    private String jwtSecret;
    @Value("${token.expiration}")
    private long jwtExpirationDate;

    public String generateToken(Authentication authentication) throws JOSEException {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        JWSSigner signer = new MACSigner(jwtSecret.getBytes(StandardCharsets.UTF_8));

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
            .subject(username)
            .issueTime(currentDate)
            .expirationTime(expireDate)
            .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS384), claimsSet);

        signedJWT.sign(signer);

        String token = signedJWT.serialize();

        return token;
    }

    public String getUsername(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return Jwts.parser()
            .verifyWith((SecretKey) key())
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }

    private Key key() {
        return new SecretKeySpec(jwtSecret.getBytes(), "HmacSHA384");
    }
}
