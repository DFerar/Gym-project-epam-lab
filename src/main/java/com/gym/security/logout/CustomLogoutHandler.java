package com.gym.security.logout;

import com.gym.entity.TokenEntity;
import com.gym.repository.security.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Slf4j
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutSuccessHandler {
    private final TokenRepository tokenRepository;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) {
        String username = request.getParameter("username");
        TokenEntity token = tokenRepository.findByUsername(username);
        if (token == null) {
            throw new BadCredentialsException("Token not found");
        }
        token.setIsValid(false);
        tokenRepository.saveAndFlush(token);
    }
}
