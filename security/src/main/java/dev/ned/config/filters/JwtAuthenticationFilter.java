package dev.ned.config.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ned.config.payload.AuthenticationRequest;
import dev.ned.config.services.AuthService;
import dev.ned.config.services.ExceptionService;
import dev.ned.config.services.PasswordValidator;
import dev.ned.config.util.JwtProperties;
import dev.ned.config.util.JwtUtil;
import dev.ned.models.User;
import dev.ned.recaptcha.services.CaptchaService;
import dev.ned.recaptcha.services.UserService;
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
import java.util.Optional;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private CaptchaService captchaService;
    private AuthService authService;
    private UserService userService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil, CaptchaService captchaService, AuthService authService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.captchaService = captchaService;
        this.authService = authService;
        this.userService = userService;
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
        if (!captchaVerified) ExceptionService.throwLoginReCaptchaException(request, response);

        PasswordValidator.ValidationResult verificationResult = authService.getVerificationResult(requestPayload.getPassword());
        if (verificationResult != PasswordValidator.ValidationResult.SUCCESS) {
            ExceptionService.throwLoginPasswordException(request, response, verificationResult.toString());
            return null;
        }

        Optional<User> userOptional = userService.getUserByEmail(requestPayload.getEmail());
        boolean emailAccountVerified = userOptional.map(User::isEnabled).orElse(true);
        if(!emailAccountVerified) {
            ExceptionService.throwEmailAccountNotConfirmedException(request, response);
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
