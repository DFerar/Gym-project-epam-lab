package com.gym.config;

import com.gym.properties.JwtProperties;
import com.gym.security.authentication.CustomAuthFilter;
import com.gym.security.jwt.CustomConverter;
import com.gym.security.jwt.CustomJwtDecoder;
import com.gym.security.jwt.JwtAuthenticationEntryPoint;
import com.gym.security.logout.CustomLogoutHandler;
import io.jsonwebtoken.Jwts;
import java.util.List;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableScheduling
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityConfig {
    private final JwtAuthenticationEntryPoint jwtAuthEntryPoint;
    private final CustomAuthFilter customAuthFilter;
    private final CustomConverter converter;
    private final CustomJwtDecoder customJwtDecoder;
    private final CustomLogoutHandler logoutHandler;

    /**
     * Configures the security filter chain.
     * Disables CSRF protection, configures CORS, sets up URL-based authorization rules, defines session management policy,
     * handles authentication exceptions, configures JWT authentication and decoding, adds custom authentication filter,
     * and sets up logout handling.
     *
     * @param http HttpSecurity object to configure the security filter chain
     * @return SecurityFilterChain object representing the configured security filter chain
     */
    @Bean
    @SneakyThrows
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(httpSecurityCorsConfigurer -> {
                UrlBasedCorsConfigurationSource source =
                    new UrlBasedCorsConfigurationSource();
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowCredentials(true);
                config.addAllowedOrigin("http://localhost**");
                config.setAllowedHeaders(List.of("Content-Type", "Authorization"));
                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
                source.registerCorsConfiguration("/**", config);
            })
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/login", "/customer/create", "/instructor/create").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint))
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {
                jwt.jwtAuthenticationConverter(
                    converter);
                jwt.decoder(customJwtDecoder);
            }))
            .addFilterBefore(customAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .logout((logout) -> logout.logoutSuccessHandler(logoutHandler))
            .httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer.authenticationEntryPoint(
                jwtAuthEntryPoint));

        return http.build();
    }

    /**
     * Provides a bean for JWT decoding.
     * Uses the JWT secret key to decode JWT tokens.
     *
     * @return JwtDecoder object for decoding JWT tokens
     */
    @Bean
    public JwtDecoder jwtDecoder(JwtProperties jwtProperties) {
        SecretKey secretKey = new SecretKeySpec(jwtProperties.getKey().getBytes(), Jwts.SIG.HS384.getId());
        return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS384).build();
    }

    /**
     * Provides a bean for AuthenticationManager.
     * Configures a DaoAuthenticationProvider with custom user details service and password encoder.
     *
     * @return AuthenticationManager object for managing authentication
     */
    @Bean
    @SneakyThrows
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }

    /**
     * Provides a bean for password encryption.
     * Uses BCryptPasswordEncoder for encrypting passwords.
     *
     * @return PasswordEncoder object for encrypting passwords
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
