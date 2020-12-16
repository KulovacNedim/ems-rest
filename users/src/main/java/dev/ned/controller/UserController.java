package dev.ned.controller;

import dev.ned.models.payloads.RoleRequestPayload;
import dev.ned.services.RoleRequestPayloadService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/role-request")
@Validated
public class UserController {
    private RoleRequestPayloadService service;

    public UserController(RoleRequestPayloadService service) {
        this.service = service;
    }

    @PostMapping
    public void processRoleRequest(@Valid @RequestBody RoleRequestPayload roleRequestPayload) {
        service.saveRoleRequestPayload(roleRequestPayload);
    }
}
