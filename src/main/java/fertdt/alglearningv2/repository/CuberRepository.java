package fertdt.alglearningv2.repository;

import fertdt.alglearningv2.model.CuberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CuberRepository extends JpaRepository<CuberEntity, UUID> {
    Optional<CuberEntity> findByNickname(String nickname);

    Optional<CuberEntity> findByEmail(String email);
}
