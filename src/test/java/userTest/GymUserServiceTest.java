package userTest;

import com.gym.entity.GymUserEntity;
import com.gym.repository.GymUserRepository;
import com.gym.service.GymUserService;
import com.gym.utils.Utils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GymUserServiceTest {
    @Mock
    private GymUserRepository gymUserRepository;
    @InjectMocks
    private GymUserService gymUserService;

    @Test
    public void shouldUpdateUser() {
        //Given
        Integer userId = 1;
        String firstName = RandomStringUtils.randomAlphabetic(7);
        String lastName = RandomStringUtils.randomAlphabetic(7);
        Boolean isActive = true;
        String password = Utils.generatePassword();

        GymUserEntity existingUser = new GymUserEntity(userId, "John", "Doe",
                "john.doe", password, false);
        when(gymUserRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(gymUserRepository.save(any(GymUserEntity.class))).thenReturn(existingUser);
        //When
        GymUserEntity result = gymUserService.updateUser(userId, firstName, lastName, isActive);
        //Assert
        assertThat(result).isEqualTo(existingUser);
    }

    @Test
    public void shouldGenerateUniqueUserName() {
        //Given
        String firstName = RandomStringUtils.randomAlphabetic(7);
        String lastName = RandomStringUtils.randomAlphabetic(7);
        String baseUserName = firstName + "." + lastName;

        when(gymUserRepository.existsByUserName(baseUserName)).thenReturn(false);
        //When
        String result = gymUserService.generateUniqueUserName(firstName, lastName);
        //Assert
        assertThat(result).isEqualTo(baseUserName);
    }

    @Test
    public void shouldGenerateUniqueUserNameWithExistingUser() {
        //Given
        String firstName = RandomStringUtils.randomAlphabetic(7);
        String lastName = RandomStringUtils.randomAlphabetic(7);
        String baseUserName = firstName + "." + lastName;
        String generatedUserName = baseUserName + "2";

        when(gymUserRepository.existsByUserName(baseUserName)).thenReturn(true);
        when(gymUserRepository.findMaxUserId()).thenReturn(1);
        //When
        String result = gymUserService.generateUniqueUserName(firstName, lastName);
        //Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(generatedUserName);
    }
}
