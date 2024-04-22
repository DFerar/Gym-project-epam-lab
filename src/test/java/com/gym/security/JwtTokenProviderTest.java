package com.gym.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.gym.properties.JwtProperties;
import com.gym.security.jwt.JwtTokenProvider;
import com.nimbusds.jose.JOSEException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {
    @Mock
    private JwtProperties jwtProperties;

    @InjectMocks
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void shouldGenerateToken() throws JOSEException {
        //Given
        when(jwtProperties.getKey()).thenReturn(RandomStringUtils.randomAlphanumeric(70));
        when(jwtProperties.getExpiration()).thenReturn(3600L); // 1 hour in milliseconds
        Authentication authentication = new UsernamePasswordAuthenticationToken(RandomStringUtils.randomAlphabetic(7),
            RandomStringUtils.randomAlphabetic(7));
        //When
        String generatedToken = jwtTokenProvider.generateToken(authentication);
        //Then
        assertThat(generatedToken).isNotNull().isNotEmpty();
    }
}