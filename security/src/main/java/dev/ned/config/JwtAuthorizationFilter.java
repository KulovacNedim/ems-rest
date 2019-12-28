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
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
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
        Authentication authentication = getUsernamePasswordAuthentication(request, response);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Continue filter execution
        chain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(JwtProperties.ACCESS_TOKEN_HEADER_STRING)
                .replace(JwtProperties.TOKEN_PREFIX, "");

        if (!token.equals("")) {
            try {
                return getAuthentication(token, response);
            } catch (TokenExpiredException exc) {
                String refreshToken = request.getHeader(JwtProperties.REFRESH_TOKEN_HEADER_STRING)
                        .replace(JwtProperties.TOKEN_PREFIX, "");
                if (!refreshToken.equals("")) {
                    return getAuthentication(refreshToken, response);
                }
//                else throw exc
            }
            return null;
            //throw exc
        }
        System.out.println("There is no access token in header");
        return null;
    }

    private Authentication getAuthentication(String token, HttpServletResponse response) {
        DecodedJWT decodedJwt = null;
        decodedJwt = JWT.require(HMAC512(JwtProperties.SECRET.getBytes()))
                .build()
                .verify(token);

        // parse the token and validate it
        String email = decodedJwt.getSubject();
        Date expiresAt = decodedJwt.getExpiresAt();
        System.out.println("at: " + expiresAt);

        // Search in the DB if we find the user by token subject (username)
        // If so, then grab user details and create spring auth token using username, pass, authorities/roles
        if (email != null) {
            User user = userRepository.findByEmail(email);
            UserPrincipal principal = new UserPrincipal(user);

            if (user.getRefreshToken().getRefreshToken().equals(token)) {
                // Create access JWT Token
                String accessJwtToken = jwtTokenProvider.createJwtToken(principal, true);

                // Create refresh JWT Token
                String refreshJwtToken = jwtTokenProvider.createJwtToken(principal, false);

                // Add access and refresh tokens in response
                jwtTokenProvider.setHeader(response, JwtProperties.ACCESS_TOKEN_HEADER_STRING, JwtProperties.TOKEN_PREFIX + accessJwtToken);
                jwtTokenProvider.setHeader(response, JwtProperties.REFRESH_TOKEN_HEADER_STRING, JwtProperties.TOKEN_PREFIX + refreshJwtToken);
            }
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, null, principal.getAuthorities());
            return auth;
        }
        return null;
    }
}
