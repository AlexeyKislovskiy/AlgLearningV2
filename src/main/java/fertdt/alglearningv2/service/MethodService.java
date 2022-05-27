package fertdt.alglearningv2.service;

import fertdt.alglearningv2.dto.response.MethodResponse;
import fertdt.alglearningv2.dto.response.SituationWithScrambleResponse;
import fertdt.alglearningv2.model.CuberEntity;

import java.util.UUID;

public interface MethodService {
    MethodResponse getMethodByName(String name, CuberEntity cuber);

    MethodResponse getMethodById(UUID methodId, CuberEntity cuber);

    void checkAll(UUID methodId, CuberEntity cuber);

    void plusAll(UUID methodId, CuberEntity cuber);

    void deleteAll(UUID methodId, CuberEntity cuber);

    MethodResponse getMethodByIdSortByPopularity(UUID methodId, CuberEntity cuber);

    MethodResponse getMethodByIdSortByLength(UUID methodId, CuberEntity cuber);

    SituationWithScrambleResponse getNextTrainingSituationInMethod(UUID methodId, CuberEntity cuber);

    SituationWithScrambleResponse getNextLearningSituationInMethod(UUID methodId, CuberEntity cuber);
}
