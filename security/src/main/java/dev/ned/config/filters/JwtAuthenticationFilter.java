package dev.ned.config.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ned.config.payload.AuthenticationRequest;
import dev.ned.config.services.AuthService;
import dev.ned.config.util.JwtProperties;
import dev.ned.config.util.JwtUtil;
import dev.ned.exceptions.ReCaptchaFailedException;
import dev.ned.recaptcha.services.CaptchaService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private CaptchaService captchaService;
    private AuthService authService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil, CaptchaService captchaService, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.captchaService = captchaService;
        this.authService = authService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AuthenticationRequest requestPayload = null;
        try {
            requestPayload = new ObjectMapper().readValue(request.getInputStream(), AuthenticationRequest.class);
        } catch (IOException e) {
            return null;
        }

        boolean captchaVerified = captchaService.verify(requestPayload.getReCaptchaToken());
        if (!captchaVerified) ReCaptchaFailedException.throwLoginReCaptchaException(request, response);

        boolean passwordVerified = authService.verifyPassword(requestPayload.getPassword());
        if (!passwordVerified) {
            AuthService.throwLoginPasswordException(request, response);
            return null;
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                requestPayload.getEmail(), requestPayload.getPassword(), new ArrayList<>());
        Authentication auth = authenticationManager.authenticate(authenticationToken);
        return auth;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String accessJwtToken = jwtUtil.generateJwtToken(authResult);
        response.setHeader(JwtProperties.ACCESS_TOKEN_HEADER_STRING, JwtProperties.TOKEN_PREFIX + accessJwtToken);
    }
}
