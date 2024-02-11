package org.rafaelvera.springsecuritydemo.services;

import org.hibernate.annotations.SecondaryRow;

@SecondaryRow
public interface TokenService {
    boolean isTokenInWhitelist(String token, String username);
}
