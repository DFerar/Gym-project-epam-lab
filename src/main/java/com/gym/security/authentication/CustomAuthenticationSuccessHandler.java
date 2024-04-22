package com.gym.security.authentication;

import com.gym.mapper.TokenMapper;
import com.gym.repository.security.TokenRepository;
import com.gym.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;
    private final TokenMapper tokenMapper;

    /**
     * Takes an HttpServletRequest, HttpServletResponse and Authentication, generates a JWT token,
     * saves it in the token repository and sets it in the Authorization header of the response.
     *
     * @param request        - the HttpServletRequest which contains the login request information.
     * @param response       - the HttpServletResponse
     * @param authentication - the Authentication object containing the authentication details.
     * @throws Exception - if the token cannot be generated, saved, or set in the response.
     */
    @Override
    @SneakyThrows
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        String jwtToken = jwtTokenProvider.generateToken(authentication);
        tokenRepository.save(tokenMapper.mapTokenToTokenEntity(jwtToken, request.getParameter("username")));
        response.addHeader("Authorization", "Bearer " + jwtToken);
    }
}
