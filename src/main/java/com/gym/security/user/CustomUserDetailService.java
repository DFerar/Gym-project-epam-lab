package com.gym.security.user;

import com.gym.entity.GymUserEntity;
import com.gym.repository.GymUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final GymUserRepository userRepository;

    /**
     * Load user-specific data by username.
     *
     * @param username - the username identifying the user whose data we want to load.
     * @return a fully populated user record (may not include password).
     * @throws UsernameNotFoundException - if the user could not be found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        GymUserEntity user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return UserDetailsImpl.build(user);
    }
}
