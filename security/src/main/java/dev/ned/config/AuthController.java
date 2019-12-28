package dev.ned.config;

import dev.ned.model.User;
import dev.ned.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public HttpStatus allowPreflightForLogin() {
        return HttpStatus.OK;
    }

    @GetMapping("/user/me")
    public ResponseEntity<User> getCurrentUser() {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        user.setPassword(null);
        user.setRefreshToken(null);
        // exception handling
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
