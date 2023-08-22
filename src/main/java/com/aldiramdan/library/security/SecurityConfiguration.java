package com.aldiramdan.library.security;

import com.aldiramdan.library.config.jwt.JwtAccessDeniedHandler;
import com.aldiramdan.library.config.jwt.JwtAuthenticationEntryPoint;
import com.aldiramdan.library.config.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.aldiramdan.library.model.entity.Role.ADMIN;
import static com.aldiramdan.library.model.entity.Role.USER;
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
    public static final String[] whiteListedRoutes = new String[]{
            "/home",
            "/auth/**",
    };

    public static final String[] getAuthListedRoutes = new String[]{
            "/users/me",
    };

    public static final String[] getWhiteListedRoutes = new String[]{
            "/authors",
            "/authors/**",
            "/categories",
            "/categories/**",
            "/genres",
            "/genres/**",
            "/publishers",
            "/publishers/**",
            "/books",
            "/books/**",
    };

    public static final String[] getAdminListedRoutes = new String[]{
            "/users",
            "/users/**",
            "/loans",
            "/loans/**",
    };

    public static final String[] postAdminListedRoutes = new String[]{
            "/authors",
            "/categories",
            "/genres",
            "/publishers",
            "/books",
            "/loans",
    };

    public static final String[] putAdminListedRoutes = new String[]{
            "/authors/**",
            "/categories/**",
            "/genres/**",
            "/publishers/**",
            "/books/**",
            "/loans/**",
    };

    public static final String[] patchAdminListedRoutes = new String[]{
            "/loan/**",
    };

    public static final String[] deleteAdminListedRoutes = new String[]{
            "/authors/**",
            "/categories/**",
            "/genres/**",
            "/publisher/**",
            "/books/**",
            "/loan/**",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf
                        .disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(whiteListedRoutes).permitAll()
                        .requestMatchers(GET, getWhiteListedRoutes).permitAll()
                        .requestMatchers(GET, getAuthListedRoutes).hasAnyAuthority(USER.name(), ADMIN.name())
                        .requestMatchers(GET, getAdminListedRoutes).hasAnyAuthority(ADMIN.name())
                        .requestMatchers(POST, postAdminListedRoutes).hasAnyAuthority(ADMIN.name())
                        .requestMatchers(PUT, putAdminListedRoutes).hasAnyAuthority(ADMIN.name())
                        .requestMatchers(PATCH, patchAdminListedRoutes).hasAnyAuthority(ADMIN.name())
                        .requestMatchers(DELETE, deleteAdminListedRoutes).hasAnyAuthority(ADMIN.name())
                        .anyRequest().authenticated())
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
