package security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.gym.entity.BlockedUserEntity;
import com.gym.repository.security.BlockedUserRepository;
import com.gym.security.authentication.LoggingAttemptsService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LoginAttemptServiceTest {
    @Mock
    private BlockedUserRepository blockedUserRepository;

    @InjectMocks
    private LoggingAttemptsService loggingAttemptsService;


    @Test
    public void shouldReturnsTrueWhenUserIsBlocked() {
        // Arrange
        String username = RandomStringUtils.randomAlphabetic(7);
        BlockedUserEntity blockedUserEntity = new BlockedUserEntity();
        blockedUserEntity.setUsername(username);
        when(blockedUserRepository.findByUsername(username)).thenReturn(blockedUserEntity);
        // Act
        boolean result = loggingAttemptsService.isBlocked(username);
        // Assert
        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnsFalseWhenUserIsNotBlocked() {
        // Arrange
        String username = RandomStringUtils.randomAlphabetic(7);
        when(blockedUserRepository.findByUsername(username)).thenReturn(null);
        // Act
        boolean result = loggingAttemptsService.isBlocked(username);
        // Assert
        assertThat(result).isFalse();
    }
}
