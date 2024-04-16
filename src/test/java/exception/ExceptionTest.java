package exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.gym.exception.CustomerNotFoundException;
import com.gym.exception.GlobalExceptionHandler;
import com.gym.exception.InstructorNotFoundException;
import com.gym.exception.LoginException;
import com.gym.exception.TrainingTypeNotFoundException;
import com.gym.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class ExceptionTest {
    @Mock
    private LoginException loginException;
    @Mock
    private CustomerNotFoundException customerNotFoundException;
    @Mock
    private InstructorNotFoundException instructorNotFoundException;
    @Mock
    private UserNotFoundException userNotFoundException;
    @Mock
    private TrainingTypeNotFoundException trainingTypeNotFoundException;
    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    public void shouldHandleLoginException() {
        //Given
        when(loginException.getMessage()).thenReturn("Invalid credentials");
        //When
        ResponseEntity<Object> response = globalExceptionHandler.handleLoginException(loginException);
        //Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isEqualTo("Wrong credentials: Invalid credentials");
    }

    @Test
    public void shouldHandleCustomerNotFoundException() {
        //Given
        when(customerNotFoundException.getMessage()).thenReturn("Customer not found");
        //When
        ResponseEntity<Object> response =
            globalExceptionHandler.handleCustomerNotFoundException(customerNotFoundException);
        //Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Cannot find customer: Customer not found");
    }

    @Test
    public void shouldHandleInstructorNotFoundException() {
        //Given
        when(instructorNotFoundException.getMessage()).thenReturn("Instructor not found");
        //When
        ResponseEntity<Object> response =
            globalExceptionHandler.handleInstructorNotFoundException(instructorNotFoundException);
        //Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Cannot find instructor: Instructor not found");
    }

    @Test
    public void shouldHandleUserNotFoundException() {
        //Given
        when(userNotFoundException.getMessage()).thenReturn("User not found");
        //When
        ResponseEntity<Object> response = globalExceptionHandler.handleIUserNotFoundException(userNotFoundException);
        //Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Cannot find user: User not found");
    }

    @Test
    public void shouldHandleTrainingTypeNotFoundException() {
        //Given
        when(trainingTypeNotFoundException.getMessage()).thenReturn("Training type not found");
        //When
        ResponseEntity<Object> response =
            globalExceptionHandler.handleITrainingTypeNotFoundException(trainingTypeNotFoundException);
        //Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Cannot find training type: Training type not found");
    }
}
