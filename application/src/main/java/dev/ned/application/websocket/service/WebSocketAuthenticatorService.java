package dev.ned.application.websocket.service;

import dev.ned.config.models.UserPrincipal;
import dev.ned.config.util.JwtUtil;
import dev.ned.models.User;
import dev.ned.recaptcha.services.UserService;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class WebSocketAuthenticatorService {

    private UserService userService;
    private JwtUtil jwtUtil;

    public WebSocketAuthenticatorService(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    // This method MUST return a UsernamePasswordAuthenticationToken instance, the spring security chain is testing it with 'instanceof' later on.
    // So don't use a subclass of it or any other class
    public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(final String  username, final String password, final String authToken) throws AuthenticationException {
        if (authToken == null || authToken.trim().isEmpty()) {
            // TO-DO: create custom exception
            throw new AuthenticationCredentialsNotFoundException("Authorization token cannot be null or empty.");
        }
        String email = jwtUtil.parseJwt(authToken).getSubject();
        Optional<User> userOptional = userService.getUserByEmail(email);
        if (userOptional.isEmpty()) return null;
        UserPrincipal principal = new UserPrincipal(userOptional.get());

        return new UsernamePasswordAuthenticationToken(
                username,
                null,
                principal.getAuthorities().size() > 0 ? principal.getAuthorities() : Collections.singleton((GrantedAuthority) () -> "ANONYMOUS_USER")
        );
    }
}