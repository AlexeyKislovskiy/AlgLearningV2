package fertdt.alglearningv2.repository;

import fertdt.alglearningv2.model.MethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MethodRepository extends JpaRepository<MethodEntity, UUID> {
    Optional<MethodEntity> findByName(String name);
}
