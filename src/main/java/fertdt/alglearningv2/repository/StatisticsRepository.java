package fertdt.alglearningv2.repository;

import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.model.StatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface StatisticsRepository extends JpaRepository<StatisticsEntity, UUID> {
    Optional<StatisticsEntity> findByDateAndCuber(LocalDate date, CuberEntity cuber);
}
