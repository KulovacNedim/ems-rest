package dev.ned.rest;

import dev.ned.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {



    @GetMapping("/")
    public String sayHello() {
        return "Hello!";
    }

    @GetMapping("/u")
    public User user() {
        return null;
    }
}
