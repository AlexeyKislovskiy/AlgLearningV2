package fertdt.alglearningv2.service;

import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.model.CuberLearningSituationEntity;

public interface CuberLearningSituationService {
    void save(CuberLearningSituationEntity cuberLearningSituationEntity);

    void delete(CuberLearningSituationEntity cuberLearningSituationEntity);

    void updateAllStatus(CuberEntity cuber);
}
