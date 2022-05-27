package fertdt.alglearningv2.util.mapper;

import fertdt.alglearningv2.dto.response.WcaCompetitionResponse;
import fertdt.alglearningv2.model.WcaCompetitionEntity;

import java.util.List;

public interface WcaCompetitionMapper {
    WcaCompetitionResponse toResponse(WcaCompetitionEntity wcaCompetitionEntity);

    List<WcaCompetitionResponse> toResponse(List<WcaCompetitionEntity> wcaCompetitionEntities);
}
