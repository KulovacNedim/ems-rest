package dev.ned.config;

import dev.ned.config.models.AuthenticationRequest;
import dev.ned.config.models.AuthenticationResponse;
import dev.ned.config.services.MyUserDetailsService;
import dev.ned.config.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

@PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

    try {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
        );
    } catch (BadCredentialsException e) {
        throw new Exception("Incorrect username or password", e);
    }

    final UserDetails userDetails = userDetailsService
            .loadUserByUsername(authenticationRequest.getUsername());

    final String jwt = jwtUtil.generateToken(userDetails);

    return ResponseEntity.ok(new AuthenticationResponse((jwt)));

}

//    private UserRepository userRepository;
//
//    public AuthController(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @PostMapping("/login")
//    public HttpStatus allowPreflightForLogin() {
//        return HttpStatus.OK;
//    }
//
//    @GetMapping("/user/me")
//    public ResponseEntity<User> getCurrentUser() {
//        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
//        Optional<User> userOptional = userRepository.findByEmail(email);
//        User user;
//        if (userOptional.isPresent()) {
//            user = userOptional.get();
//            user.setPassword(null);
//            user.setRefreshToken(null);
//        } else {
//            throw new ResourceNotFoundException("User", "email", email);
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(user);
//    }
}
