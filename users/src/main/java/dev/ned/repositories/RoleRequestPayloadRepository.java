package dev.ned.repositories;

import dev.ned.models.payloads.RoleRequestPayload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRequestPayloadRepository extends JpaRepository<RoleRequestPayload, Long> {
}
