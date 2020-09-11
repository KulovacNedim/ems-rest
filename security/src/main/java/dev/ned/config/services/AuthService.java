package dev.ned.config.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ned.config.payload.AuthenticationRequest;
import dev.ned.exceptions.*;
import dev.ned.helpers.EmailTypeIdentifierEnum;
import dev.ned.mailer.SendGridMailer;
import dev.ned.models.EmailConfirm;
import dev.ned.models.User;
import dev.ned.payloads.ApiError;
import dev.ned.recaptcha.services.CaptchaService;
import dev.ned.services.EmailConfirmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Service
public class AuthService {
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private EmailConfirmService emailConfirmService;

    @Autowired
    SendGridMailer sendGridMailer;

    @Autowired
    CaptchaService captchaService;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder, EmailConfirmService emailConfirmService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.emailConfirmService = emailConfirmService;
    }

    public String signUp(@Valid AuthenticationRequest authPayload) throws Exception {
        boolean captchaVerified = captchaService.verify(authPayload.getReCaptchaToken());
        if (!captchaVerified) throw new ReCaptchaFailedException();

        boolean passwordVerified = verifyPassword(authPayload.getPassword());
        if (!passwordVerified) throw new PasswordNotAcceptedException();

        Optional<User> userOptional = userService.getUserByEmail(authPayload.getEmail());
        if (userOptional.isPresent()) throw new EmailExistsException(authPayload.getEmail());

        User user = new User();
        user.setEnabled(false);
        user.setLocked(false);
        user.setEmail(authPayload.getEmail());
        user.setPassword(passwordEncoder.encode(authPayload.getPassword()));
        user.setFirstName("first name");
        user.setLastName("last name");
        //roles and permissions are not set

        User savedUser = userService.save(user);
        if (savedUser == null) throw new Exception("Error occurred. User could not be saved in the database.");

        String randomString = UUID.randomUUID().toString();
        Date expirationDate = new Date(System.currentTimeMillis() + 300_000); // 5 minutes
        EmailConfirm emailConfirm = new EmailConfirm(savedUser, randomString, expirationDate);
        EmailConfirm savedEmailConfirm = emailConfirmService.save(emailConfirm);
        if (savedEmailConfirm == null) throw new Exception("Error occurred. Data could not be saved in the database.");

        Map<String, String> data = new HashMap<>();
        data.put("link", randomString);
        int status = sendGridMailer.sendPersonalizedEmail(user.getEmail(), data, EmailTypeIdentifierEnum.EMAIL_CONFIRMATION);
        if (199 < status && status < 300) return user.getEmail();

        // set notification that new registration is made

        throw new EmailCouldNotBeSentException(user.getEmail());
    }

    public boolean verifyPassword(String password) {
        boolean hasNumbers = password.trim().matches("[a-zA-Z ]*\\d+.*");
        boolean hasLetters = password.trim().matches(".*[a-z].*");
        boolean isLongEnough = password.trim().length() > 7;
        return hasNumbers && hasLetters && isLongEnough;
    }

    public static void throwLoginPasswordException(HttpServletRequest request, HttpServletResponse response) {
        PasswordNotAcceptedException ex = new PasswordNotAcceptedException();
        response.setStatus(400);
        response.setContentType("application/json");
        try (
                PrintWriter out = response.getWriter()) {
            final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex.getMessage());
            out.print(new ObjectMapper().writeValueAsString(apiError));
            out.flush();
        } catch (
                IOException e) {
            throw new InvalidPayloadException();
        }
    }
}
