package dev.ned.rest;

import dev.ned.model.User;
import dev.ned.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class TestController {

    private UserRepository userRepository;

    public TestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //    private UserQueryRepository userQueryRepository;
//
//    public TestController(UserQueryRepository userQueryRepository) {
//        this.userQueryRepository = userQueryRepository;
//    }

//    @GetMapping
//    public List<User> getAllUsers() {
//        return this.userQueryRepository.getAllUsers();
//    }
//
//    @GetMapping("/{id}")
//    public Optional<User> getBoardGameById(@PathVariable Long id) {
//        return this.userQueryRepository.getUserById(id);
////                .orElseThrow(ResourceNotFoundException::new);
//    }

    @GetMapping("/")
    public String sayHello() {
        return "Hello!";
    }

    @GetMapping(value = "/q")
    public List<String> sayHsello() {
        List<String> l = new ArrayList<>();
        l.add("zx");
        l.add("as");
        return l;
    }

    @GetMapping("/u")
    public User user() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Nedim");
        user.setLastName("Kulovac");
        user.setActive(true);
        user.setEmail("email");
        user.setPassword("pass");
        user.setPermissions("perm");
        return user;
    }
}
