package dev.ned.repositories;

import dev.ned.models.NotEnabledReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotEnabledReasonRepository extends JpaRepository<NotEnabledReason, Long> {

    @Query("FROM NotEnabledReason WHERE user_id = ?1")
    List<NotEnabledReason> findAllNotEnabledReasonsForUser(Long userID);
}