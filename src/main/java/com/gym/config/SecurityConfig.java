package com.gym.config;

import com.gym.security.authentication.CustomAuthFilter;
import com.gym.security.jwt.CustomConverter;
import com.gym.security.jwt.CustomJwtDecoder;
import com.gym.security.jwt.JwtAuthenticationEntryPoint;
import com.gym.security.logout.CustomLogoutHandler;
import com.gym.security.user.CustomUserDetailService;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
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
public class SecurityConfig {
    private final JwtAuthenticationEntryPoint jwtAuthEntryPoint;
    private final CustomUserDetailService userDetailService;
    private final CustomAuthFilter customAuthFilter;
    private final CustomConverter converter;
    private final CustomJwtDecoder customJwtDecoder;
    private final CustomLogoutHandler logoutHandler;
    @Value("${token.signing.key}")
    private String jwtSecret;

    /**
     * Configures the security filter chain.
     * Disables CSRF protection, configures CORS, sets up URL-based authorization rules, defines session management policy,
     * handles authentication exceptions, configures JWT authentication and decoding, adds custom authentication filter,
     * and sets up logout handling.
     *
     * @param http HttpSecurity object to configure the security filter chain
     *
     * @return SecurityFilterChain object representing the configured security filter chain
     *
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
                config.addAllowedOrigin("*");
                config.addAllowedHeader("*");
                config.addAllowedMethod("*");
                source.registerCorsConfiguration("/**", config);
            })
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/login", "/customer/create", "/instructor/create").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint))
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(
                converter)))
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(customJwtDecoder)))
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
     *
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKey secretKey = new SecretKeySpec(jwtSecret.getBytes(), SignatureAlgorithm.HS384.getJcaName());
        return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS384).build();
    }

    /**
     * Provides a bean for AuthenticationManager.
     * Configures a DaoAuthenticationProvider with custom user details service and password encoder.
     *
     * @return AuthenticationManager object for managing authentication
     *
     */
    @Bean
    @SneakyThrows
    public AuthenticationManager authenticationManager() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }

    /**
     * Provides a bean for password encryption.
     * Uses BCryptPasswordEncoder for encrypting passwords.
     *
     * @return PasswordEncoder object for encrypting passwords
     *
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}