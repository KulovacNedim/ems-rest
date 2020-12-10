package dev.ned.services;

import dev.ned.models.NotEnabledReason;
import dev.ned.models.User;
import dev.ned.repositories.NotEnabledReasonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserNotEnabledReasonService {
    private NotEnabledReasonRepository notEnabledReasonRepository;

    public UserNotEnabledReasonService(NotEnabledReasonRepository notEnabledReasonRepository) {
        this.notEnabledReasonRepository = notEnabledReasonRepository;
    }

    public NotEnabledReason save(NotEnabledReason notEnabledReason) {
        return notEnabledReasonRepository.save(notEnabledReason);
    }

    public NotEnabledReason updateNotEnabledReason(NotEnabledReason notEnabledReason) {
        return notEnabledReasonRepository.save(notEnabledReason);
    }

    public List<NotEnabledReason> findAllNotEnabledReasonsForUser(User user) {
        return notEnabledReasonRepository.findAllNotEnabledReasonsForUser(user.getId());
    }

}
