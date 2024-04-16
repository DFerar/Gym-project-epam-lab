package auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gym.entity.GymUserEntity;
import com.gym.repository.GymUserRepository;
import com.gym.service.PasswordChangeService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class ChangePasswordServiceTest {
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private GymUserRepository gymUserRepository;
    @InjectMocks
    private PasswordChangeService passwordChangeService;


    @Test
    public void shouldChangeUserPassword() {
        //Given
        String username = RandomStringUtils.randomAlphabetic(7);
        String oldPassword = RandomStringUtils.randomAlphabetic(7);
        String newPassword = RandomStringUtils.randomAlphabetic(7);
        GymUserEntity gymUserEntity = new GymUserEntity();
        gymUserEntity.setUserName(username);
        gymUserEntity.setPassword(oldPassword);

        when(gymUserRepository.findByUserName(username)).thenReturn(gymUserEntity);
        when(passwordEncoder.encode(newPassword)).thenReturn(newPassword);
        when(passwordEncoder.matches(oldPassword, gymUserEntity.getPassword())).thenReturn(true);

        //When
        passwordChangeService.changeUsersPassword(username, oldPassword, newPassword);

        //Assert
        assertThat(gymUserEntity.getPassword()).isEqualTo(newPassword);
        verify(gymUserRepository, times(1)).save(gymUserEntity);
    }
}
