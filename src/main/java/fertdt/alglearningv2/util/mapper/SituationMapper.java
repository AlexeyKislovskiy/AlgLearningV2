package fertdt.alglearningv2.util.mapper;

import fertdt.alglearningv2.dto.response.SituationResponse;
import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.model.SituationEntity;

import java.util.List;

public interface SituationMapper {
    SituationResponse toResponse(SituationEntity situationEntity, CuberEntity cuber);

    List<SituationResponse> toResponse(List<SituationEntity> situationEntities, CuberEntity cuber);
}
