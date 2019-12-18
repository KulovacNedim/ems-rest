package dev.ned.repositories.seeder;

import dev.ned.model.User;
import dev.ned.model.Role;
import dev.ned.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
        admin.setUsername("admin");
        admin.setActive(true);
        admin.setEmail("admin@gmail.com");
        admin.setPassword(passwordEncoder.encode("adm123"));
        admin.setFirstName("admin name");
        admin.setLastName("admin name");
        admin.setPermissions("qq");
        List<Role> adminRoles = Arrays.asList(new Role("ADMIN", admin), new Role("CEO", admin));
        admin.setRoles(adminRoles);


        User teacher = new User();
        teacher.setUsername("teacher@gmail.com");
        teacher.setActive(true);
        teacher.setPermissions("11");
        teacher.setEmail("teacher");
        teacher.setPassword(passwordEncoder.encode("tea123"));
        teacher.setFirstName("teacher name");
        teacher.setLastName("last name");
        List<Role> teacherRoles = Arrays.asList(new Role("TEACHER", teacher));
        teacher.setRoles(teacherRoles);

        List<User> users = Arrays.asList(admin, teacher);

        this.userRepository.saveAll(users);

    }
}

