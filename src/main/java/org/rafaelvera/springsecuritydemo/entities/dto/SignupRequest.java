package org.rafaelvera.springsecuritydemo.entities.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record SignupRequest(
        @NotBlank
        @Length(max = 25)
        String username,

        @NotBlank
        @Length(min = 8)
        @Pattern(regexp = "^[-a-zA-Z0-9]*$")
        String password
) {
}
