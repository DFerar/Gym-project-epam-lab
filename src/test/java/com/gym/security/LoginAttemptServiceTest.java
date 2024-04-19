package com.gym.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gym.entity.GymUserEntity;
import com.gym.exception.UserNotFoundException;
import com.gym.repository.GymUserRepository;
import com.gym.repository.security.TokenRepository;
import com.gym.security.authentication.LoggingAttemptsService;
import java.time.LocalDateTime;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoginAttemptServiceTest {

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private GymUserRepository gymUserRepository;

    @InjectMocks
    private LoggingAttemptsService loggingAttemptsService;


    @Test
    public void shouldThrowUserNotFoundException() {
        //Given
        String username = RandomStringUtils.randomAlphanumeric(7);
        //When & Assert
        assertThatExceptionOfType(UserNotFoundException.class)
            .isThrownBy(() -> loggingAttemptsService.isBlocked(username));
    }

    @Test
    public void shouldBlockUser() {
        //Given
        String username = RandomStringUtils.randomAlphanumeric(7);
        GymUserEntity gymUserEntity = new GymUserEntity();
        gymUserEntity.setTimeOfBlocking(LocalDateTime.now().minusMinutes(4));
        when(gymUserRepository.findByUserName(username)).thenReturn(gymUserEntity);
        //When & Assert
        assertThat(loggingAttemptsService.isBlocked(username)).isTrue();
    }

    @Test
    public void shouldDeleteTokens() {
        //Given
        loggingAttemptsService.deleteTokens();
        //When & Assert
        verify(tokenRepository, times(1)).deleteAll();
    }
}
