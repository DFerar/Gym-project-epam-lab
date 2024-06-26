package com.gym.security.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class CustomAuthFilter extends AbstractAuthenticationProcessingFilter {
    private final LoggingAttemptsService loggingAttemptsService;

    /**
     * Constructor for the CustomAuthFilter that sets up the AuthenticationManager, and the handlers for successful and failed authentication.
     *
     * @param loggingAttemptsService             the service to log attempts
     * @param authenticationManager              the manager that handles the whole authentication process
     * @param customAuthenticationSuccessHandler the handler which proceed after successful authentication
     * @param customAuthenticationFailureHandler the handler which proceed after failed authentication
     */
    public CustomAuthFilter(LoggingAttemptsService loggingAttemptsService,
                            @Lazy AuthenticationManager authenticationManager,
                            CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler,
                            CustomAuthenticationFailureHandler customAuthenticationFailureHandler) {
        super(new AntPathRequestMatcher("/login", "POST"), authenticationManager);
        this.loggingAttemptsService = loggingAttemptsService;
        setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        setAuthenticationFailureHandler(customAuthenticationFailureHandler);
    }

    /**
     * Attempts to authenticate a user based on a HttpServletRequest.
     *
     * @param request  -  the HttpServletRequest containing the username and password parameters.
     * @param response - the HttpServletResponse.
     * @return Authentication - a fully authenticated object including credentials.
     * @throws BadCredentialsException - if the authentication process is unsuccessful
     */
    @Override
    @SneakyThrows
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("username:{}", username);
        log.info("password:{}", password);
        if (loggingAttemptsService.isBlocked(username)) {
            log.info("username:{} is blocked", username);
            throw new BadCredentialsException("User is blocked");
        }
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        return getAuthenticationManager().authenticate(authToken);
    }
}
