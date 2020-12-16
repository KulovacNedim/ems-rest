package dev.ned.controller;

import dev.ned.models.role.RoleRequestPayload;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/role-request")
//@CrossOrigin("*")
public class UserController {

    @PostMapping
    public void processRoleRequest(@RequestBody RoleRequestPayload roleRequestPayload) {

        System.out.println(roleRequestPayload.toString());

    }
}
