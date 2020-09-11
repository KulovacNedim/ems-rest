package dev.ned.config.filters;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.ned.config.models.UserPrincipal;
import dev.ned.recaptcha.services.UserService;
import dev.ned.config.util.JwtProperties;
import dev.ned.config.util.JwtUtil;
import dev.ned.models.User;
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
import java.util.Optional;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private UserService userService;
    private JwtUtil jwtUtil;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(JwtProperties.ACCESS_TOKEN_HEADER_STRING);
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        Authentication authentication = null;
        try {
            authentication = getUsernamePasswordAuthentication(request, response);
        } catch (Exception e) {
            chain.doFilter(request, response);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String token = request.getHeader(JwtProperties.ACCESS_TOKEN_HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");
        if (!token.equals("")) {
            try {
                return getAuthentication(token);
            } catch (TokenExpiredException exc) {
                return null;
            }
        }
        return null;
    }

    private Authentication getAuthentication(String token) {
        DecodedJWT decodedJwt = jwtUtil.parseJwt(token);
        String email = decodedJwt.getSubject();
        Optional<User> userOptional = userService.getUserByEmail(email);
        if (email != null && !email.equals("") && userOptional.isPresent()) {
            UserPrincipal principal = new UserPrincipal(userOptional.get());
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, null, principal.getAuthorities());
            return auth;
        }
        return null;
    }
}
