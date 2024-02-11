package org.rafaelvera.springsecuritydemo.repositories;

import org.rafaelvera.springsecuritydemo.entities.TokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<TokenEntity, Long> {
    Optional<TokenEntity> findByTokenAndUser_Username(String token, String username);
}
