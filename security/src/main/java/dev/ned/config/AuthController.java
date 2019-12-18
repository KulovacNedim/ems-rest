package dev.ned.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @PostMapping("/login")
    public HttpStatus allowPreflightForLogin() {
        return HttpStatus.OK;
    }
}
