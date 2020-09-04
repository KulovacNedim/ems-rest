package dev.ned.config.repositories;

import dev.ned.config.models.EmailConfirm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailConfirmRepository extends JpaRepository<EmailConfirm, Long> {
}
