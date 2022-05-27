package fertdt.alglearningv2.util.mapper.impl;

import fertdt.alglearningv2.dto.response.WcaCompetitionResponse;
import fertdt.alglearningv2.model.WcaCompetitionEntity;
import fertdt.alglearningv2.util.mapper.WcaCompetitionMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WcaCompetitionMapperImpl implements WcaCompetitionMapper {
    @Override
    public WcaCompetitionResponse toResponse(WcaCompetitionEntity wcaCompetitionEntity) {
        return WcaCompetitionResponse.builder()
                .name(wcaCompetitionEntity.getName())
                .link(wcaCompetitionEntity.getLink())
                .coordinates(wcaCompetitionEntity.getCoordinates())
                .build();
    }

    @Override
    public List<WcaCompetitionResponse> toResponse(List<WcaCompetitionEntity> wcaCompetitionEntities) {
        return wcaCompetitionEntities.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
