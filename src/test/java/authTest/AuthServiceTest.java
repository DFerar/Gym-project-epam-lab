package authTest;

import com.gym.repository.CustomerRepository;
import com.gym.repository.InstructorRepository;
import com.gym.service.AuthenticationService;
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
    public void matchingCustomerCredentialsTest() {
        String userName = "Test.User";
        String password = "password";

        when(customerRepository.existsByGymUserEntity_UserNameAndGymUserEntity_Password(userName, password))
                .thenReturn(true);

        boolean result = authenticationService.matchCustomerCredentials(userName, password);

        assertThat(result).isTrue();
    }

    @Test
    public void matchingInstructorCredentialsTest() {
        String userName = "Test.User";
        String password = "password";

        when(instructorRepository.existsByGymUserEntity_UserNameAndGymUserEntity_Password(userName, password))
                .thenReturn(true);

        boolean result = authenticationService.matchInstructorCredentials(userName, password);

        assertThat(result).isTrue();
    }
}
