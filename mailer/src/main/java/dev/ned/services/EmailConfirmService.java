package dev.ned.services;

import dev.ned.models.EmailConfirm;
import dev.ned.repositories.EmailConfirmRepository;
import org.springframework.stereotype.Service;

@Service
public class EmailConfirmService {
    private EmailConfirmRepository emailConfirmRepository;

    public EmailConfirmService(EmailConfirmRepository emailConfirmRepository) {
        this.emailConfirmRepository = emailConfirmRepository;
    }

    public EmailConfirm save(EmailConfirm emailConfirm) {
        return emailConfirmRepository.save(emailConfirm);
    }
}
