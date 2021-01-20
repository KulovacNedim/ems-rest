package dev.ned.repositories;

import dev.ned.models.app_users.PersonAuthorizedToTakeStudentsInAndOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonAuthorizedToTakeStudentsInAndOutRepository extends JpaRepository<PersonAuthorizedToTakeStudentsInAndOut, Long> {
}
