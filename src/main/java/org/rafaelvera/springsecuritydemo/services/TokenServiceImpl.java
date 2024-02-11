package org.rafaelvera.springsecuritydemo.services;

import lombok.RequiredArgsConstructor;
import org.rafaelvera.springsecuritydemo.repositories.TokenRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;

    @Override
    public boolean isTokenInWhitelist(String token, String username) {
        return this.tokenRepository
                .findByTokenAndUser_Username(token, username)
                .isEmpty();
    }
}
