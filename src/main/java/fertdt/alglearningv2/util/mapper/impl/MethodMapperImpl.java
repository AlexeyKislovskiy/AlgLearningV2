package fertdt.alglearningv2.util.mapper.impl;

import fertdt.alglearningv2.dto.response.MethodResponse;
import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.model.MethodEntity;
import fertdt.alglearningv2.util.MethodUtil;
import fertdt.alglearningv2.util.mapper.MethodMapper;
import fertdt.alglearningv2.util.mapper.SituationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MethodMapperImpl implements MethodMapper {
    private final SituationMapper situationMapper;

    @Override
    public MethodResponse toResponse(MethodEntity methodEntity, CuberEntity cuber) {
        return MethodResponse.builder()
                .id(methodEntity.getId())
                .description(methodEntity.getDescription())
                .image(methodEntity.getImage())
                .name(methodEntity.getName())
                .situations(situationMapper.toResponse(methodEntity.getSituations(), cuber))
                .learningStatus(MethodUtil.getMethodLearningStatus(methodEntity, cuber))
                .numberOfLearned(MethodUtil.numberOfLearned(methodEntity))
                .numberOfLearning(MethodUtil.numberOfLearning(methodEntity))
                .numberOfLearnedSituations(MethodUtil.numberOfLearnedSituations(methodEntity, cuber))
                .numberOfLearningSituations(MethodUtil.numberOfLearningSituations(methodEntity, cuber))
                .numberOfNotLearningSituations(MethodUtil.numberOfNotLearningSituations(methodEntity, cuber))
                .numberOfSituations(MethodUtil.numberOfSituations(methodEntity))
                .numberOfNew(MethodUtil.getNumberOfNew(methodEntity, cuber))
                .numberOfRepeat(MethodUtil.getNumberOfRepeat(methodEntity, cuber))
                .numberOfForgot(MethodUtil.getNumberOfForgot(methodEntity, cuber))
                .build();
    }

    @Override
    public List<MethodResponse> toResponse(List<MethodEntity> methodEntities, CuberEntity cuber) {
        return methodEntities.stream().map(method -> toResponse(method, cuber)).collect(Collectors.toList());
    }
}
