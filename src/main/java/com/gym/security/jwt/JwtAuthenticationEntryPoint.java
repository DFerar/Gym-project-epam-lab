package com.gym.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * This method will be triggered anytime a user tries to access a resource that requires authentication and they do not provide
     * valid authentication credentials. It sends an HTTP status code 401 (Unauthorised) in the response.
     *
     * @param request       - the HttpServletRequest
     * @param response      - the HttpServletResponse
     * @param authException - the AuthenticationException thrown
     */
    @Override
    @SneakyThrows
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) {

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}