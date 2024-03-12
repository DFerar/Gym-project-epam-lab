package authTest;

import com.gym.repository.CustomerRepository;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private InstructorRepository instructorRepository;
    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    public void shouldMatchCustomerCredentials() {
        //Given
        String userName = RandomStringUtils.randomAlphabetic(7);
        String password = Utils.generatePassword();

        when(customerRepository.existsByGymUserEntity_UserNameAndGymUserEntity_Password(userName, password))
                .thenReturn(true);
        //When
        boolean result = authenticationService.matchCustomerCredentials(userName, password);
        //Asserts
        assertThat(result).isTrue();
    }

    @Test
    public void shouldMatchInstructorCredentials() {
        //Given
        String userName = RandomStringUtils.randomAlphabetic(7);
        String password = Utils.generatePassword();

        when(instructorRepository.existsByGymUserEntity_UserNameAndGymUserEntity_Password(userName, password))
                .thenReturn(true);
        //When
        boolean result = authenticationService.matchInstructorCredentials(userName, password);
        //Assert
        assertThat(result).isTrue();
    }
}
