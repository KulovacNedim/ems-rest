package dev.ned.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.ned.model.User;
import dev.ned.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Read the Authorization header, where the JWT token should be
        String header = request.getHeader(JwtProperties.ACCESS_TOKEN_HEADER_STRING);

        // If header does not contain BEARER or is null delegate to Spring impl and exit
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        // If header is present, try grab user principal from database and perform authorization
        Authentication authentication = getUsernamePasswordAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Continue filter execution
        chain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token = request.getHeader(JwtProperties.ACCESS_TOKEN_HEADER_STRING)
                .replace(JwtProperties.TOKEN_PREFIX, "");

        if (token != null) {
            DecodedJWT decodedJwt = null;
            try {
                decodedJwt = JWT.require(HMAC512(JwtProperties.SECRET.getBytes()))
                        .build()
                        .verify(token);

                // parse the token and validate it
                String email = decodedJwt.getSubject();

                System.out.println("132 " + email);

                // Search in the DB if we find the user by token subject (username)
                // If so, then grab user details and create spring auth token using username, pass, authorities/roles
                if (email != null) {
                    User user = userRepository.findByEmail(email);

                    UserPrincipal principal = new UserPrincipal(user);

                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, null, principal.getAuthorities());
                    return auth;
                }

            } catch (TokenExpiredException exc) {
                String refreshToken = request.getHeader(JwtProperties.REFRESH_TOKEN_HEADER_STRING)
                        .replace(JwtProperties.TOKEN_PREFIX, "");
                if (refreshToken != null) {
                    DecodedJWT decodedRefreshToken = JWT.require(HMAC512(JwtProperties.SECRET.getBytes()))
                            .build()
                            .verify(refreshToken);
                    String email = decodedRefreshToken.getSubject();
                    //check if email is null
                    User user = userRepository.findByEmail(email);
                    if (user.getRefreshToken().getRefreshToken().equals(refreshToken)) {
                        // get new access token and refresh token
                        // put them to header
                        System.out.println("Here is your auth");

                        UserPrincipal principal = new UserPrincipal(user);

                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, null, principal.getAuthorities());

                        return auth;

                    }
                }
//                else throw exc
            }
            return null;
            //throw exc
        }
        return null;
    }
}
