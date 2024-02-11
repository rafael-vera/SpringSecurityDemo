package org.rafaelvera.springsecuritydemo.config;

import lombok.RequiredArgsConstructor;
import org.rafaelvera.springsecuritydemo.config.filters.AuthenticationFilter;
import org.rafaelvera.springsecuritydemo.config.filters.AuthorizationFilter;
import org.rafaelvera.springsecuritydemo.config.jwt.JwtUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private static final String[] WHITE_LIST = {
            "/api/v1/public/**"
    };

    private final AuthorizationFilter authorizationFilter;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;
    private final LogoutHandler logoutService;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http, AuthenticationManager authenticationManager
    ) throws Exception {
        final AuthenticationFilter authenticationFilter = new AuthenticationFilter(this.jwtUtils);
        authenticationFilter.setAuthenticationManager(authenticationManager);
        authenticationFilter.setFilterProcessesUrl("/api/v1/auth/login");

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(WHITE_LIST).permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .addFilter(authenticationFilter)
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(this.logoutService)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()))
                .build();
    }

    @Bean
    AuthenticationManager authenticationManager(
            HttpSecurity httpSecurity,
            PasswordEncoder passwordEncoder
    ) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity
                .getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder);

        return authenticationManagerBuilder.build();
    }
}
