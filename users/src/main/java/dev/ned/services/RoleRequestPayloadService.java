package dev.ned.services;

import dev.ned.models.payloads.RoleRequestPayload;
import dev.ned.repositories.RoleRequestPayloadRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleRequestPayloadService {
    private RoleRequestPayloadRepository repository;

    public RoleRequestPayloadService(RoleRequestPayloadRepository repository) {
        this.repository = repository;
    }

    public void saveRoleRequestPayload(RoleRequestPayload payload) {
        repository.save(payload);
        // notify admin
    }
}
