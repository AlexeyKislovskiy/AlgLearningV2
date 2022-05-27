package fertdt.alglearningv2.service;

import fertdt.alglearningv2.dto.response.MethodResponse;
import fertdt.alglearningv2.dto.response.SituationResponse;
import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.model.SituationEntity;

import java.util.List;
import java.util.UUID;

public interface SituationService {
    SituationEntity getSituationEntityById(UUID situationId);

    SituationEntity getSituationEntityByIdForApi(UUID situationId);

    UUID getMethodIdBySituationId(UUID situationId);

    void check(UUID situationId, CuberEntity cuber);

    void plus(UUID situationId, CuberEntity cuber);

    void delete(UUID situationId, CuberEntity cuber);

    List<SituationResponse> getSituationsByMethodIdAndSearchExpression(UUID methodId, String searchExpression, CuberEntity cuber);

    List<SituationResponse> getTrainingSituationsFromMethod(MethodResponse methodResponse, CuberEntity cuber);

    void changeTrainingSituation(UUID situationId, CuberEntity cuber);

    List<SituationResponse> getLearningSituationsFromMethod(MethodResponse methodResponse, CuberEntity cuber);

    void learn(UUID situationId, String difficulty, CuberEntity cuber);

    void train(CuberEntity cuber);
}
