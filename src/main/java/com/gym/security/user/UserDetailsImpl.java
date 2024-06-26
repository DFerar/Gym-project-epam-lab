package com.gym.security.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gym.entity.GymUserEntity;
import java.io.Serial;
import java.util.Collection;
import java.util.Objects;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    /**
     * Constructor.
     */
    public UserDetailsImpl(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    /**
     * Builder.
     */
    public static UserDetailsImpl build(GymUserEntity user) {
        return new UserDetailsImpl(user.getId(),
            user.getUserName(),
            user.getPassword());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
