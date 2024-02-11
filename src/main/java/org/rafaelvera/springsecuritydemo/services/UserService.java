package org.rafaelvera.springsecuritydemo.services;

import org.rafaelvera.springsecuritydemo.entities.dto.SignupRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    boolean createUser(SignupRequest signupRequest);
}
