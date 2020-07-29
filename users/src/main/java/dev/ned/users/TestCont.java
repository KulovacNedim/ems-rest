package dev.ned.users;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestCont {

    @GetMapping("/")
    public String getHome() {
        return "nice to see, you whoever you are";
    }

    @GetMapping("/user")
    public String getUser() {
        return "nice to see you on USER page";
    }

    @GetMapping("/admin")
    public String getAdmin() {
        return "nice to see you on ADMIN page";
    }
}
