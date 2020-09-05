package dev.ned.repositories;

import dev.ned.models.EmailConfirm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailConfirmRepository extends JpaRepository<EmailConfirm, Long> {
}
