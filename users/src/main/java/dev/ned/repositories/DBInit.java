package dev.ned.repositories;

import dev.ned.models.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class DBInit implements CommandLineRunner {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public DBInit(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public void run(String... args) throws Exception {
        User admin = new User();
        admin.setEnabled(false);
        admin.setLocked(false);
        admin.setEmail("admin@gmail.com");
        admin.setPassword(passwordEncoder.encode("adm12345"));
        admin.setFirstName("admin name");
        admin.setLastName("admin name");
        admin.addPermission(new Permission("EDIT_PROFILE"));
        admin.addRole(new Role("ADMIN"));
        admin.addRole(new Role("CEO"));


        User teacher = new User();
        teacher.setEnabled(true);
        teacher.setLocked(false);
        teacher.addPermission(new Permission("ACCESS_API"));
        teacher.setEmail("teacher@gmail.com");
        teacher.setPassword(passwordEncoder.encode("tea12345"));
        teacher.setFirstName("teacher name");
        teacher.setLastName("last name");
        teacher.addRole(new Role("TEACHER"));
        NotEnabledReason reason = new NotEnabledReason(UserNotEnabledReasons.MISSING_ROLE, new Date(), teacher, true);
        teacher.setNotEnabledReasons(new ArrayList<>());
        teacher.getNotEnabledReasons().add(reason);
        List<User> users = Arrays.asList(admin, teacher);

        this.userRepository.saveAll(users);

    }
}

