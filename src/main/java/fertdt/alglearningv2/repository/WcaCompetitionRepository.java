package fertdt.alglearningv2.repository;

import fertdt.alglearningv2.model.WcaCompetitionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WcaCompetitionRepository extends JpaRepository<WcaCompetitionEntity, UUID> {
}
