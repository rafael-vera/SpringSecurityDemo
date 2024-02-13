package org.rafaelvera.springsecuritydemo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rafaelvera.springsecuritydemo.entities.enums.Role;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name = "username", nullable = false, length = 100, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "enum ('USER', 'ADMIN') default 'USER'")
    private Role role;

    @Column(nullable = false, columnDefinition = "bit(1) default 0")
    private boolean locked;

    @Column(nullable = false, columnDefinition = "bit(1) default 0")
    private boolean enable;

    @OneToMany(mappedBy = "user")
    private Set<TokenEntity> tokens;
}
