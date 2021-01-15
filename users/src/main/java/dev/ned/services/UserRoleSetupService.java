package dev.ned.services;

import dev.ned.models.Notification;
import dev.ned.models.NotificationType;
import dev.ned.models.Role;
import dev.ned.models.User;
import dev.ned.models.payloads.RoleRequestPayload;
import dev.ned.repositories.NotificationRepository;
import dev.ned.repositories.RoleRepository;
import dev.ned.repositories.RoleRequestPayloadRepository;
import dev.ned.repositories.UserRepository;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UserRoleSetupService {
    private final RoleRequestPayloadRepository roleRequestPayloadRepository;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SimpMessageSendingOperations simpMessagingTemplate;

    public UserRoleSetupService(RoleRequestPayloadRepository repository, NotificationRepository notificationRepository, UserRepository userRepository, RoleRepository roleRepository, SimpMessageSendingOperations simpMessagingTemplate) {
        this.roleRequestPayloadRepository = repository;
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void saveRoleRequestPayload(RoleRequestPayload payload) {
        roleRequestPayloadRepository.save(payload);

        // List of roles to be notified
        List<Role> rolesToNotify = roleRepository.findRolesByRoleNames(Arrays.asList("TEACHER", "ADMIN"));
        Notification notificationSaved = notificationRepository.save(getNotification(payload, rolesToNotify));

        // Send notification to client
        this.simpMessagingTemplate.convertAndSend("/topic/notifications", notificationSaved);
    }

    private Notification getNotification(RoleRequestPayload payload, List<Role> rolesToNotify) {
        Notification notification = new Notification();
        notification.setMessage("Request for role setup");
        notification.setType(NotificationType.ROLE_SETUP_REQUEST);
        notification.setCreatedAt(new Date());
        notification.addArg("payload_id", String.valueOf(payload.getId()));
        notification.addArg("parent_first_name", payload.getParentData().getFirstName());
        notification.addArg("parent_last_name", payload.getParentData().getLastName());
        AtomicInteger i = new AtomicInteger();
        payload.getStudentData().forEach(entity -> {
            notification.addArg(i + "_student_first_name", entity.getFirstName());
            notification.addArg(i + "_student_last_name", entity.getLastName());
            i.getAndIncrement();
        });
        notification.addArg("number_of_students", String.valueOf(payload.getStudentData().size()));

        for (Role role : rolesToNotify) {
            notification.getRolesToNotify().add(role);
        }
        return notification;
    }

    public Set<Notification> getNotifications(String email) throws Exception {
        Optional<User> authUser = userRepository.findByEmail(email);
        if(authUser.isEmpty()) throw new Exception("Wrong credentials.");
        authUser.get().setPassword(null);

        List<Notification> notificationForMe = notificationRepository.findUserBasedNotifications(authUser.get());
        List<Notification> notificationsForMyRoles = notificationRepository.findRoleBasedNotifications(authUser.get().getRoles());

        Set<Notification> setOfNotifications = new HashSet<>();
        setOfNotifications.addAll(notificationForMe);
        setOfNotifications.addAll(notificationsForMyRoles);

        return setOfNotifications;
    }
}
