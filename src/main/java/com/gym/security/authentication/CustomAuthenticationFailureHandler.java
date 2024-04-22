package com.gym.security.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final LoggingAttemptsService loggingAttemptsService;

    /**
     * Takes an HttpServletRequest, HttpServletResponse and AuthenticationException, records the failed authentication attempt and throws a BadCredentialsException.
     *
     * @param request   - the HttpServletRequest which contains the login request information.
     * @param response  - the HttpServletResponse
     * @param exception - the AuthenticationException triggered by the failure to authenticate.
     * @throws BadCredentialsException - if the provided credentials are invalid.
     */
    @Override
    @SneakyThrows
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) {
        String username = request.getParameter("username");
        loggingAttemptsService.addUserToLoginAttemptsCache(username);
        SecurityContextHolder.clearContext();
        throw new BadCredentialsException("Username or password is incorrect");
    }
}
