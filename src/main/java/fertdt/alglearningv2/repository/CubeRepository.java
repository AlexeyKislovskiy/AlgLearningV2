package fertdt.alglearningv2.repository;

import fertdt.alglearningv2.model.CubeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CubeRepository extends JpaRepository<CubeEntity, UUID> {
    Optional<CubeEntity> findByName(String name);
}
