package user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

        GymUserEntity existingUser =
            new GymUserEntity(userId, RandomStringUtils.randomAlphabetic(7), RandomStringUtils.randomAlphabetic(7),
                RandomStringUtils.randomAlphabetic(7), password, false, 1);

        GymUserEntity newGymUserEntity =
            new GymUserEntity(userId, firstName, lastName, existingUser.getUserName(), password, isActive, 1);
        when(gymUserRepository.findByUserName(existingUser.getUserName())).thenReturn(existingUser);
        when(gymUserRepository.save(any(GymUserEntity.class))).thenReturn(newGymUserEntity);
        //When
        GymUserEntity result = gymUserService.updateUser(newGymUserEntity);
        //Assert
        assertThat(result).isEqualTo(newGymUserEntity);
    }

    @Test
    public void shouldCreateCustomer() {
        //Given
        Long userId = 1L;
        String firstName = RandomStringUtils.randomAlphabetic(7);
        String lastName = RandomStringUtils.randomAlphabetic(7);
        Boolean isActive = true;
        String password = Utils.generatePassword();

        GymUserEntity existingUser =
            new GymUserEntity(userId, firstName, lastName, RandomStringUtils.randomAlphabetic(7), password, isActive,
                1);

        when(gymUserRepository.existsByUserName(any(String.class))).thenReturn(false);
        when(gymUserRepository.save(any())).thenReturn(existingUser);
        //When
        GymUserEntity savedUser = gymUserService.createUser(existingUser);

        assertThat(savedUser).isEqualTo(existingUser);
    }

}
