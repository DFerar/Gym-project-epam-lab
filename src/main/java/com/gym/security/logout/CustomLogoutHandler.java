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
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomLogoutHandler implements LogoutSuccessHandler {
    private final TokenRepository tokenRepository;

    /**
     * Invokes on successfully logout, invalidates token of the user.
     *
     * @param request        - the HttpServletRequest
     * @param response       - the HttpServletResponse
     * @param authentication - the Authentication object containing user details
     * @throws BadCredentialsException - if the token not found in the repository
     */
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
