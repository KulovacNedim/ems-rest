package dev.ned.config.services;

import dev.ned.config.payload.AuthenticationRequest;
import dev.ned.exceptions.*;
import dev.ned.helpers.EmailTypeIdentifierEnum;
import dev.ned.mailer.SendGridMailer;
import dev.ned.models.EmailConfirm;
import dev.ned.models.NotEnabledReason;
import dev.ned.models.User;
import dev.ned.models.UserNotEnabledReasons;
import dev.ned.recaptcha.services.CaptchaService;
import dev.ned.recaptcha.services.UserService;
import dev.ned.services.EmailConfirmService;
import dev.ned.services.UserNotEnabledReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.DiscriminatorValue;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static dev.ned.config.services.PasswordValidator.*;

@Service
public class AuthService {
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private EmailConfirmService emailConfirmService;

    @Autowired
    SendGridMailer sendGridMailer;

    @Autowired
    CaptchaService captchaService;

    @Autowired
    UserNotEnabledReasonService userNotEnabledReasonService;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder, EmailConfirmService emailConfirmService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.emailConfirmService = emailConfirmService;
    }

    public String signUp(@Valid AuthenticationRequest authPayload) throws Exception {
        boolean captchaVerified = captchaService.verify(authPayload.getReCaptchaToken());
        if (!captchaVerified) throw new ReCaptchaFailedException();

        ValidationResult verificationResult = getVerificationResult(authPayload.getPassword());
        if (verificationResult != ValidationResult.SUCCESS)
            throw new PasswordNotAcceptedException(verificationResult.toString());

        Optional<User> userOptional = userService.getUserByEmail(authPayload.getEmail());
        if (userOptional.isPresent()) throw new EmailExistsException(authPayload.getEmail());

        // extract to helper method
        User user = new User();
        user.setEnabled(false);
        user.setLocked(false);
        user.setEmail(authPayload.getEmail().toLowerCase());
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
        data.put("email", user.getEmail());
        int status = sendGridMailer.sendPersonalizedEmail(user.getEmail(), data, EmailTypeIdentifierEnum.EMAIL_CONFIRMATION);
        if (199 < status && status < 300) {
            userNotEnabledReasonService.save(new NotEnabledReason(UserNotEnabledReasons.EMAIL_NOT_VERIFIED, new Date(), savedUser, true));
            return user.getEmail();
        }

        // set notification that new registration is made

        throw new EmailCouldNotBeSentException(user.getEmail());
    }

    public ValidationResult getVerificationResult(String password) {
        return isLongEnough(8)
                .and((hasLetters()))
                .and(hasNumbers())
                .apply(password);
    }

    public boolean confirmEmail(String email, String hash) throws Exception {
        Optional<User> userOptional = userService.getUserByEmail(email);
        if (userOptional.isEmpty()) throw new ResourceNotFoundException("User", "email", email);
        User user = userOptional.get();

        Optional<EmailConfirm> emailConfirmOptional = emailConfirmService.findOneByUserId(user.getId());
        if (emailConfirmOptional.isEmpty()) {
            throw new PasswordConfirmationException("Confirmation request does not exist for this user.");
        }

        boolean confirmationExpired = new Date(System.currentTimeMillis()).after(emailConfirmOptional.get().getExpirationDate());
        if (confirmationExpired) {
            emailConfirmService.deleteByUserId(user.getId());
            userService.deleteUser(user);
            throw new PasswordConfirmationException("Five minutes period for confirmation expired. Please register again");
        }

        boolean doesMatch = emailConfirmOptional.get().getRandomString().equals(hash);
        if (!doesMatch)
            throw new PasswordConfirmationException("Provided data does not match existing one. Please register again");

        user.setEnabled(true);
        User userSaved = userService.save(user);
        if (userSaved != null) {
            userNotEnabledReasonService.findAllNotEnabledReasonsForUser(userSaved)
                .stream()
                .filter(r -> r.isValid() == true && r.getReason().equals(UserNotEnabledReasons.EMAIL_NOT_VERIFIED))
                .findFirst()
                .ifPresent(reason -> {
                    reason.setValid(false);
                    userNotEnabledReasonService.updateNotEnabledReason(reason);
                });

            emailConfirmService.deleteByUserId(user.getId());
            return true;
        }
        return false;
    }
}
