package dev.ned.config.controllers;

import dev.ned.config.models.ApiResponse;
import dev.ned.config.payload.AuthenticationRequest;
import dev.ned.config.services.AuthService;
import dev.ned.exceptions.InvalidTokenException;
import dev.ned.exceptions.ResourceNotFoundException;
import dev.ned.models.User;
import dev.ned.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private UserRepository userRepository;
    private AuthService authService;

    public AuthController(UserRepository userRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        if (email.equals("anonymousUser")) throw new InvalidTokenException();
        return userRepository.findByEmail(email)
                .map(user -> {
                    user.setPassword(null);
                    user.setNotEnabledReasons(user.getNotEnabledReasons().stream().filter(r -> r.isValid()).collect(Collectors.toList()));
                    return ResponseEntity.status(HttpStatus.OK).body(user);
                })
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody AuthenticationRequest authPayload) throws Exception {
        ApiResponse res = new ApiResponse(true, String.format("User account with email %s registered successfully", authService.signUp(authPayload)));
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/email-confirmation/{email}/{hash}")
    public ResponseEntity<?> confirmEmail(@PathVariable("email") String email, @PathVariable("hash") String hash) throws Exception {
        boolean success = authService.confirmEmail(email, hash);
        ApiResponse res = new ApiResponse(success, "Email confirmed. Please log in.");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
