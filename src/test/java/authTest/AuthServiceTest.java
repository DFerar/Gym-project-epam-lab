package authTest;

import com.gym.entity.GymUserEntity;
import com.gym.exceptionHandler.LoginException;
import com.gym.repository.CustomerRepository;
import com.gym.repository.GymUserRepository;
import com.gym.repository.InstructorRepository;
import com.gym.service.AuthenticationService;
import com.gym.utils.Utils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private InstructorRepository instructorRepository;
    @Mock
    private GymUserRepository gymUserRepository;
    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    public void shouldThrowExceptionMatchCustomerCredentials() {
        //Given
        String userName = RandomStringUtils.randomAlphabetic(7);
        String password = Utils.generatePassword();

        when(customerRepository.existsByGymUserEntityUserNameAndGymUserEntityPassword(userName, password))
                .thenReturn(false);
        //When & Assert
        assertThatThrownBy(() -> authenticationService.matchCustomerCredentials(userName, password)).isInstanceOf(
                LoginException.class);
    }

    @Test
    public void shouldTrowExceptionMatchInstructorCredentials() {
        //Given
        String userName = RandomStringUtils.randomAlphabetic(7);
        String password = Utils.generatePassword();

        when(instructorRepository.existsByGymUserEntityUserNameAndGymUserEntityPassword(userName, password))
                .thenReturn(false);
        //When & Assert
        assertThatThrownBy(() -> authenticationService.matchInstructorCredentials(userName, password)).isInstanceOf(
                LoginException.class);
    }

    @Test
    public void shouldChangeUserPassword() {
        //Given
        String username = RandomStringUtils.randomAlphabetic(7);
        String oldPassword = RandomStringUtils.randomAlphabetic(7);
        String newPassword = RandomStringUtils.randomAlphabetic(7);
        GymUserEntity gymUserEntity = new GymUserEntity();
        gymUserEntity.setUserName(username);
        gymUserEntity.setPassword(oldPassword);

        when(gymUserRepository.existsByUserNameAndPassword(username, oldPassword)).thenReturn(true);
        when(gymUserRepository.findByUserName(username)).thenReturn(gymUserEntity);

        //When
        authenticationService.changeUsersPassword(username, oldPassword, newPassword);

        //Assert
        assertThat(gymUserEntity.getPassword()).isEqualTo(newPassword);
        verify(gymUserRepository, times(1)).save(gymUserEntity);
    }

    @Test
    public void shouldThrowExceptionMatchUserCredentials() {
        //Given
        String username = RandomStringUtils.randomAlphabetic(7);
        String password = RandomStringUtils.randomAlphabetic(7);

        when(gymUserRepository.existsByUserNameAndPassword(username, password)).thenReturn(false);
        //When & Assert
        assertThatThrownBy(() -> authenticationService.matchCredentials(username, password)).isInstanceOf(
                SecurityException.class);
    }
}
