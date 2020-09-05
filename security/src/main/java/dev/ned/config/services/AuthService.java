package dev.ned.config.services;

import dev.ned.config.payload.AuthenticationRequest;
import dev.ned.exceptions.EmailCouldNotBeSentException;
import dev.ned.exceptions.EmailExistsException;
import dev.ned.exceptions.ReCaptchaFailedException;
import dev.ned.helpers.EmailTypeIdentifierEnum;
import dev.ned.mailer.SendGridMailer;
import dev.ned.models.EmailConfirm;
import dev.ned.models.User;
import dev.ned.recaptcha.services.CaptchaService;
import dev.ned.services.EmailConfirmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
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
        if(!captchaVerified) throw new ReCaptchaFailedException();

        Optional<User> userOptional = userService.getUserByEmail(authPayload.getEmail());
        if(userOptional.isPresent()) throw new EmailExistsException(authPayload.getEmail());

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
        if (savedEmailConfirm == null)  throw new Exception("Error occurred. Data could not be saved in the database.");

        Map<String, String> data = new HashMap<>();
        data.put("link", randomString);
        int status = sendGridMailer.sendPersonalizedEmail(user.getEmail(), data, EmailTypeIdentifierEnum.EMAIL_CONFIRMATION);
        if (199 < status && status < 300) return user.getEmail();

        // set notification that new registration is made

        throw new EmailCouldNotBeSentException(user.getEmail());
    }
}
