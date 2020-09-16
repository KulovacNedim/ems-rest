package dev.ned.repositories;

import dev.ned.models.EmailConfirm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface EmailConfirmRepository extends JpaRepository<EmailConfirm, Long> {
    @Query(value = "SELECT * FROM email_confirm WHERE user_id = (:id)", nativeQuery = true)
    public Optional<EmailConfirm> findByUserId(@Param("id")Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM EmailConfirm WHERE user_id = ?1")
    public void deleteByUserId(Long id);
}
