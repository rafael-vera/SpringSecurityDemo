package org.rafaelvera.springsecuritydemo.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.rafaelvera.springsecuritydemo.config.jwt.JwtUtils;
import org.rafaelvera.springsecuritydemo.entities.TokenEntity;
import org.rafaelvera.springsecuritydemo.entities.UserEntity;
import org.rafaelvera.springsecuritydemo.repositories.TokenRepository;
import org.rafaelvera.springsecuritydemo.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutHandler {
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String tokenHeader = request.getHeader("Authorization");

        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            final String token = tokenHeader.substring(7);
            final String username = this.jwtUtils.extractUsername(token);

            final Optional<UserEntity> userOpt = this.userRepository.findByUsername(username);
            if (userOpt.isEmpty())
                return;

            this.tokenRepository.save(
                    TokenEntity.builder()
                            .token(token)
                            .user(userOpt.get())
                            .build()
            );
        }
    }
}
