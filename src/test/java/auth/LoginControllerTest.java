package auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.gym.controller.PasswordChangeController;
import com.gym.dto.request.login.ChangeLoginRequestDto;
import com.gym.service.PasswordChangeService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {
    @Mock
    private PasswordChangeService passwordChangeService;

    @InjectMocks
    private PasswordChangeController passwordChangeController;


    @Test
    public void shouldChangePassword() {
        // Given
        ChangeLoginRequestDto requestDto = new ChangeLoginRequestDto();
        requestDto.setUserName(RandomStringUtils.randomAlphabetic(7));
        requestDto.setOldPassword(RandomStringUtils.randomAlphabetic(7));
        requestDto.setNewPassword(RandomStringUtils.randomAlphabetic(7));

        // When
        ResponseEntity<String> response = passwordChangeController.changeLogin(requestDto);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(passwordChangeService, times(1)).changeUsersPassword(requestDto.getUserName(),
            requestDto.getOldPassword(), requestDto.getNewPassword());
    }
}
