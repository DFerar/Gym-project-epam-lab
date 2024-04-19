package com.gym.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.gym.entity.GymUserEntity;
import com.gym.repository.GymUserRepository;
import com.gym.security.user.CustomUserDetailService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailServiceTest {

    @Mock
    private GymUserRepository userRepository;

    @InjectMocks
    private CustomUserDetailService userDetailService;

    @Test
    public void shouldReturnUserDetails() {
        //Given
        String username = RandomStringUtils.randomAlphanumeric(7);
        GymUserEntity userEntity = new GymUserEntity();
        userEntity.setUserName(username);
        userEntity.setPassword(RandomStringUtils.randomAlphanumeric(7));

        when(userRepository.findByUserName(username)).thenReturn(userEntity);
        //When
        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        //Then
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(username);
    }

    @Test
    public void shouldThrowException() {
        //Given
        String username = RandomStringUtils.randomAlphanumeric(7);
        when(userRepository.findByUserName(username)).thenReturn(null);

        //When & Then
        assertThatThrownBy(() -> userDetailService.loadUserByUsername(username))
            .isInstanceOf(UsernameNotFoundException.class)
            .hasMessage(username);
    }
}
