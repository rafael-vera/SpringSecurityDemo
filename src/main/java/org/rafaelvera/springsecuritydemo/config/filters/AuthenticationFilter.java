package org.rafaelvera.springsecuritydemo.config.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.rafaelvera.springsecuritydemo.config.jwt.JwtUtils;
import org.rafaelvera.springsecuritydemo.entities.dto.LoginRequest;
import org.rafaelvera.springsecuritydemo.entities.dto.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtils jwtUtils;

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        String username, password;

        try {
            LoginRequest loginReq = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginRequest.class);

            // Validate Record
            // Add custom Exception

            username = loginReq.username();
            password = loginReq.password();
        } catch (IOException e) {
            throw new RuntimeException(e); // Write on response
        }

        return this.getAuthenticationManager()
                .authenticate(
                        new UsernamePasswordAuthenticationToken(username, password)
                );
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        final String token = this.jwtUtils.generateToken(user);

        response.addHeader("Authorization", token);
        response.getWriter().write(
                new ObjectMapper()
                        .writeValueAsString(
                                new LoginResponse(user.getUsername(), token)
                        )
        );
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
