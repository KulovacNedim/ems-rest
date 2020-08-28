package dev.ned.config.services;

//import com.sendgrid.Request;
//import com.sendgrid.Response;
//import com.sendgrid.SendGrid;
//import com.sendgrid.helpers.mail.Mail;
//import com.sendgrid.helpers.mail.objects.Email;
//import com.sendgrid.helpers.mail.objects.Content;
//import com.sendgrid.Method;

import dev.ned.config.payload.AuthenticationRequest;
import dev.ned.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

//import java.lang.reflect.Method;

@Service
public class AuthService {
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean signUp(@Valid AuthenticationRequest requestPayload) throws IOException {
        // check if user already exists
        Optional<User> userOptional = userService.getUserByEmail(requestPayload.getEmail());
        User user = null;
        if(userOptional.isPresent()) return false;

        user = new User();
        user.setEnabled(false);
        user.setLocked(false);
        user.setEmail(requestPayload.getEmail());
        user.setPassword(passwordEncoder.encode(requestPayload.getPassword()));
        user.setFirstName("first name");
        user.setLastName("last name");
        //roles and permissions are not set

        // set notification that new registration is made


        User savedUser = userService.save(user);

//        Email from = new Email("ems.app.setup@gmail.com");
//        String subject = "Sending with SendGrid is Fun";
//        Email to = new Email("nedim.kulovac@gmail.com");
//        Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
//        Mail mail = new Mail(from, subject, to, content);
//
//        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
//        Request request = new Request();
//        try {
//            request.setMethod(Method.POST);
//            request.setEndpoint("mail/send");
//            request.setBody(mail.build());
//            Response response = sg.api(request);
//            System.out.println(response.getStatusCode());
//            System.out.println(response.getBody());
//            System.out.println(response.getHeaders());
//        } catch (IOException ex) {
//            throw ex;
//        }

        return true;
    }
}
