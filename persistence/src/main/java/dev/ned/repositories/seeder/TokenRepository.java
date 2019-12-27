package dev.ned.repositories.seeder;

import dev.ned.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<RefreshToken, Long> {
//    @Override
//    <S extends RefreshToken> S save(S s);
}
