package com.aldiramdan.library.security;

import com.aldiramdan.library.config.jwt.JwtAccessDeniedHandler;
import com.aldiramdan.library.config.jwt.JwtAuthenticationEntryPoint;
import com.aldiramdan.library.config.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.aldiramdan.library.model.entity.Role.ADMIN;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    public static final String[] whiteListedRoutes = new String[] {
            "/auth/**",
            "/error",
            "/authors",
            "/categories",
            "/genres",
            "/publisher"
    };

    public static final String [] getAdminListedRoutes = new String[] {
            "/users",
            "/users/**"
    };

    public static final String[] adminListedRoutes = new String[] {
            "/managements/**",
            "/authors",
            "/categories",
            "/genres",
            "/publisher"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf
                        .disable())
                .cors(cors -> cors
                        .disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(whiteListedRoutes)
                        .permitAll()
                        .requestMatchers(GET, whiteListedRoutes)
                        .permitAll()
                        .requestMatchers(GET, getAdminListedRoutes)
                        .hasAnyAuthority(ADMIN.name())
                        .requestMatchers(POST, adminListedRoutes)
                        .hasAnyAuthority(ADMIN.name())
                        .requestMatchers(PUT, adminListedRoutes)
                        .hasAnyAuthority(ADMIN.name())
                        .requestMatchers(DELETE, adminListedRoutes)
                        .hasAnyAuthority(ADMIN.name())
                        .anyRequest()
                        .authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(jwtAccessDeniedHandler))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .build();
    }
}
