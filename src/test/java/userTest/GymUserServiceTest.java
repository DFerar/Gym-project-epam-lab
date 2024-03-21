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
        Long userId = 1L;

        String firstName = RandomStringUtils.randomAlphabetic(7);
        String lastName = RandomStringUtils.randomAlphabetic(7);
        Boolean isActive = true;
        String password = Utils.generatePassword();

        GymUserEntity existingUser = new GymUserEntity(userId, RandomStringUtils.randomAlphabetic(7),
                RandomStringUtils.randomAlphabetic(7),
                RandomStringUtils.randomAlphabetic(7), password, false);

        GymUserEntity newGymUserEntity = new GymUserEntity(userId, firstName, lastName, existingUser.getUserName(),
                password, isActive);
        when(gymUserRepository.findByUserName(existingUser.getUserName())).thenReturn(existingUser);
        when(gymUserRepository.save(any(GymUserEntity.class))).thenReturn(newGymUserEntity);
        //When
        GymUserEntity result = gymUserService.updateUser(newGymUserEntity);
        //Assert
        assertThat(result).isEqualTo(newGymUserEntity);
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
        when(gymUserRepository.findMaxUserId()).thenReturn(1L);
        //When
        String result = gymUserService.generateUniqueUserName(firstName, lastName);
        //Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(generatedUserName);
    }
}
