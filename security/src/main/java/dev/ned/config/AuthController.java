package dev.ned.config;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

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
