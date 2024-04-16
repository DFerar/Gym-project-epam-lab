package com.gym.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final HttpServletRequest request;

    /**
     * Converts a Jwt token into an UsernamePasswordAuthenticationToken.
     *
     * @param token - the Jwt token to convert.
     * @return AbstractAuthenticationToken - the converted token.
     */
    @Override
    public AbstractAuthenticationToken convert(Jwt token) {
        String tokenValue = request.getHeader("Authorization");

        if (tokenValue != null && tokenValue.startsWith("Bearer ")) {
            tokenValue = tokenValue.substring(7);
        }

        String username = jwtTokenProvider.getUsername(tokenValue);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
        );
    }
}
