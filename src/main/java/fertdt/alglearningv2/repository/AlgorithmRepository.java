package fertdt.alglearningv2.repository;

import fertdt.alglearningv2.model.AlgorithmEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AlgorithmRepository extends JpaRepository<AlgorithmEntity, UUID> {
    Optional<AlgorithmEntity> findByTextAndSituation_Id(String text, UUID situationId);

    List<AlgorithmEntity> findAllByVerified(boolean verified);
}
