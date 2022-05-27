package fertdt.alglearningv2.repository;

import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.model.CuberLearningSituationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface CuberLearningSituationRepository extends JpaRepository<CuberLearningSituationEntity, UUID> {
    @Modifying
    @Transactional
    @Query("UPDATE CuberLearningSituationEntity cle SET cle.status='REPEAT' WHERE cle IN " +
            "(SELECT situation FROM CuberLearningSituationEntity situation WHERE" +
            " situation.cuber=:cuber AND situation.status='AWAIT'" +
            " AND situation.nextRepeat <= CURRENT_DATE)")
    void updateAllStatus(CuberEntity cuber);
}
