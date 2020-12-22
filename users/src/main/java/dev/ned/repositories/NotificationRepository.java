package dev.ned.repositories;

import dev.ned.models.Notification;
import dev.ned.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n, IN (n.rolesToNotify) r where r in (:roles)")
    List<Notification> findNotificationsForRoles(List<Role> roles);
}
