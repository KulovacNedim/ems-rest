package dev.ned.recaptcha.services;

import dev.ned.models.User;
import dev.ned.repositories.NotEnabledReasonRepository;
import dev.ned.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private NotEnabledReasonRepository notEnabledReasonRepository;

    public UserService(UserRepository userRepository, NotEnabledReasonRepository notEnabledReasonRepository) {
        this.userRepository = userRepository;
        this.notEnabledReasonRepository = notEnabledReasonRepository;
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

}
