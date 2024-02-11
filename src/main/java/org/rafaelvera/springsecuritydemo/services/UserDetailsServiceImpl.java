package org.rafaelvera.springsecuritydemo.services;

import lombok.RequiredArgsConstructor;
import org.rafaelvera.springsecuritydemo.entities.UserEntity;
import org.rafaelvera.springsecuritydemo.entities.dto.SignupRequest;
import org.rafaelvera.springsecuritydemo.entities.enums.Role;
import org.rafaelvera.springsecuritydemo.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = this.userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario '"+username+"' no existe."));

        return new User(
                user.getUsername(),
                user.getPassword(),
                user.isEnable(),
                true,
                true,
                !user.isLocked(),
                Collections.singleton(
                        new SimpleGrantedAuthority(user.getRole().name())
                )
        );
    }

    @Override
    public boolean createUser(SignupRequest signupRequest) {
        UserEntity user = this.userRepository.save(
                UserEntity.builder()
                        .username(signupRequest.username())
                        .password(
                                this.passwordEncoder.encode(
                                        signupRequest.password()))
                        .role(Role.USER)
                        .enable(true)
                        .build()
        );

        return this.userRepository.existsById(user.getId());
    }
}
