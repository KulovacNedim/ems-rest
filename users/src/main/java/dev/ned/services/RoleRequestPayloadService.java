package dev.ned.services;

import dev.ned.models.Notification;
import dev.ned.models.NotificationType;
import dev.ned.models.Role;
import dev.ned.models.User;
import dev.ned.models.payloads.RoleRequestPayload;
import dev.ned.repositories.NotificationRepository;
import dev.ned.repositories.RoleRequestPayloadRepository;
import dev.ned.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class RoleRequestPayloadService {
    private RoleRequestPayloadRepository roleRequestPayloadRepository;
    private NotificationRepository notificationRepository;
    private UserRepository userRepository;

    public RoleRequestPayloadService(RoleRequestPayloadRepository repository, NotificationRepository notificationRepository, UserRepository userRepository) {
        this.roleRequestPayloadRepository = repository;
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public void saveRoleRequestPayload(RoleRequestPayload payload) {
        roleRequestPayloadRepository.save(payload);

        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User authUser = userRepository.findByEmail(email).get();


        Notification notification = new Notification();

        for (Role role : authUser.getRoles()){
            notification.getRolesToNotify().add(role);
        }

        // notificationDTO.setUserToNotify(authUser);
        notification.setMessage("Request for role setup");
        notification.setType(NotificationType.ROLE_SETUP_REQUEST);
        notification.setCreatedAt(new Date());
        notification.addArg("payload_id", String.valueOf(payload.getId()));
        notification.addArg("parent_first_name", payload.getParentData().getFirstName());
        notification.addArg("parent_last_name", payload.getParentData().getLastName());
        notification.addArg("student_first_name", payload.getStudentData().getFirstName());
        notification.addArg("student_last_name", payload.getStudentData().getLastName());

        notificationRepository.save(notification);
        // notify admin
    }
}
