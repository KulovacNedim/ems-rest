package dev.ned.repositories;

import dev.ned.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r where r.roleName in (:roleNames)")
    List<Role> findRolesByRoleNames(List<String> roleNames);
}
