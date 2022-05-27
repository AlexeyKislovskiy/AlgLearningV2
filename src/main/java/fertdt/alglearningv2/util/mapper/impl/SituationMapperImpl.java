package fertdt.alglearningv2.util.mapper.impl;

import fertdt.alglearningv2.dto.response.SituationResponse;
import fertdt.alglearningv2.dto.response.SituationSimpleResponse;
import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.model.SituationEntity;
import fertdt.alglearningv2.util.SituationUtil;
import fertdt.alglearningv2.util.mapper.AlgorithmMapper;
import fertdt.alglearningv2.util.mapper.SituationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SituationMapperImpl implements SituationMapper {
    private final AlgorithmMapper algorithmMapper;

    @Override
    public SituationResponse toResponse(SituationEntity situationEntity, CuberEntity cuber) {
        SituationResponse situationResponse = SituationResponse.builder()
                .id(situationEntity.getId())
                .image(situationEntity.getImage())
                .name(situationEntity.getName())
                .algorithms(algorithmMapper.toResponse(situationEntity.getAlgorithms(), cuber))
                .learningStatus(SituationUtil.getSituationLearningStatus(situationEntity, cuber))
                .trainingStatus(SituationUtil.getSituationTrainingStatus(situationEntity, cuber))
                .cuberLearningSituationStatus(SituationUtil.getCuberLearningSituationStatus(situationEntity, cuber))
                .build();
        situationResponse.setMirror(toSimpleResponse(situationEntity.getMirror(), cuber));
        situationResponse.setReverse(toSimpleResponse(situationEntity.getReverse(), cuber));
        situationResponse.setMirrorReverse(toSimpleResponse(situationEntity.getMirrorReverse(), cuber));
        return situationResponse;

    }

    @Override
    public List<SituationResponse> toResponse(List<SituationEntity> situationEntities, CuberEntity cuber) {
        return situationEntities.stream().map(situation -> toResponse(situation, cuber)).collect(Collectors.toList());
    }

    private SituationSimpleResponse toSimpleResponse(SituationEntity situationEntity, CuberEntity cuber) {
        return SituationSimpleResponse.builder()
                .id(situationEntity.getId())
                .image(situationEntity.getImage())
                .name(situationEntity.getName())
                .algorithms(algorithmMapper.toResponse(situationEntity.getAlgorithms(), cuber))
                .learningStatus(SituationUtil.getSituationLearningStatus(situationEntity, cuber))
                .trainingStatus(SituationUtil.getSituationTrainingStatus(situationEntity, cuber))
                .cuberLearningSituationStatus(SituationUtil.getCuberLearningSituationStatus(situationEntity, cuber))
                .build();
    }

}
