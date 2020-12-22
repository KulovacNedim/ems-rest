package dev.ned.controller;

import dev.ned.models.Notification;
import dev.ned.models.payloads.RoleRequestPayload;
import dev.ned.services.UserRoleSetupService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import dev.ned.exceptions.InvalidTokenException;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/api")
@Validated
public class UserRoleSetupController {
    private final UserRoleSetupService userRoleSetupService;

    public UserRoleSetupController(UserRoleSetupService userRoleSetupService) {
        this.userRoleSetupService = userRoleSetupService;
    }

    @PostMapping(path = "/role-request", consumes = {"application/json"})
    public void processRoleRequest(@Valid @RequestBody RoleRequestPayload roleRequestPayload) {
        userRoleSetupService.saveRoleRequestPayload(roleRequestPayload);
    }

    @GetMapping("/my-notifications")
    public Set<Notification> getNotifications() throws Exception {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        if (email.equals("anonymousUser")) throw new InvalidTokenException();
        return userRoleSetupService.getNotifications(email);
    }
}
