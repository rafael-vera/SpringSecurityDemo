package org.rafaelvera.springsecuritydemo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.rafaelvera.springsecuritydemo.entities.dto.SignupRequest;
import org.rafaelvera.springsecuritydemo.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class Controller {
    private final UserService userService;

    @GetMapping("/public/index")
    public ResponseEntity<?> getPublic() {
        return ResponseEntity.ok("Hello World!");
    }

    @PostMapping("/public/signup")
    public ResponseEntity<?> createUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (!this.userService.createUser(signupRequest)) {
            return ResponseEntity.internalServerError().body("The user can not be created.");
        }
        return ResponseEntity.ok("Created new user");
    }

    @GetMapping("/private/user")
    public ResponseEntity<?> getUser() {
        return ResponseEntity.ok("Hello User!");
    }

    @GetMapping("/private/admin")
    public ResponseEntity<?> getAdmin() {
        return ResponseEntity.ok("Hello Admin!");
    }
}
