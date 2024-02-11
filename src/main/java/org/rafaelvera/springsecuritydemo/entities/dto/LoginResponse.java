package org.rafaelvera.springsecuritydemo.entities.dto;

public record LoginResponse(
        String username,
        String token
) {
}
