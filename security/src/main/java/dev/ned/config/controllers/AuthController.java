package dev.ned.config.controllers;

import dev.ned.config.exceptions.ResourceNotFoundException;
import dev.ned.models.User;
import dev.ned.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @PostMapping("/login")
//    public HttpStatus allowPreflightForLogin() {
//        return HttpStatus.OK;
//    }

    @GetMapping("/user/me")
    public ResponseEntity<User> getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            user.setPassword(null);
        } else {
            throw new ResourceNotFoundException("User", "email", email);
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
