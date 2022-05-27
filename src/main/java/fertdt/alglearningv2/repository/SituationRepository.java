package fertdt.alglearningv2.repository;

import fertdt.alglearningv2.model.SituationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface SituationRepository extends JpaRepository<SituationEntity, UUID>, JpaSpecificationExecutor<SituationEntity> {
}
