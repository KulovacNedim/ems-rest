package dev.ned.config;

import com.auth0.jwt.JWT;
import dev.ned.model.RefreshToken;
import dev.ned.model.User;
import dev.ned.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Service
public class JwtTokenProvider {

    UserRepository userRepository;

    public JwtTokenProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    protected String createAccessJwtToken(Authentication authentication) {
        UserPrincipal principal = getPrincipal(authentication);
        String token = JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .sign(HMAC512(JwtProperties.SECRET.getBytes()));
        return token;
    }

    protected String createRefreshJwtToken(Authentication authentication) {
        UserPrincipal principal = getPrincipal(authentication);
        String token = JWT.create()
                .withSubject(principal.getUsername())
                .sign(HMAC512(JwtProperties.SECRET.getBytes()));

        // store refresh token in db
        User user = userRepository.findByEmail(principal.getUsername());
        user.setRefreshToken(new RefreshToken(token));
        this.userRepository.save(user);
        return token;
    }

    private UserPrincipal getPrincipal(Authentication authentication) {
        return (UserPrincipal) authentication.getPrincipal();
    }

    protected void setHeader(HttpServletResponse response, String name, String value) {
        response.setHeader(name, value);
    }
}
