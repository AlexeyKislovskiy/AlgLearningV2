package fertdt.alglearningv2.util.mapper.impl;

import fertdt.alglearningv2.constants.SettingConstants;
import fertdt.alglearningv2.dto.response.AlgorithmApiResponse;
import fertdt.alglearningv2.dto.response.AlgorithmResponse;
import fertdt.alglearningv2.model.AlgorithmEntity;
import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.model.SettingEntity;
import fertdt.alglearningv2.service.SettingService;
import fertdt.alglearningv2.util.AlgorithmUtil;
import fertdt.alglearningv2.util.AlgorithmsSortingUtil;
import fertdt.alglearningv2.util.mapper.AlgorithmMapper;
import fertdt.alglearningv2.util.mapper.CuberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AlgorithmMapperImpl implements AlgorithmMapper {
    private final CuberMapper cuberMapper;
    private final SettingService settingService;

    @Override
    public AlgorithmResponse toResponse(AlgorithmEntity algorithmEntity, CuberEntity cuber) {
        return AlgorithmResponse.builder()
                .id(algorithmEntity.getId())
                .text(algorithmEntity.getText())
                .verified(algorithmEntity.isVerified())
                .numberOfUses(AlgorithmUtil.numberOfUses(algorithmEntity))
                .usingStatus(AlgorithmUtil.getAlgorithmUsingStatus(algorithmEntity, cuber))
                .addCuber(cuberMapper.toResponse(algorithmEntity.getAddCuber()))
                .build();
    }

    @Override
    public AlgorithmApiResponse toResponse(AlgorithmEntity algorithmEntity) {
        return AlgorithmApiResponse.builder()
                .id(algorithmEntity.getId())
                .text(algorithmEntity.getText())
                .verified(algorithmEntity.isVerified())
                .numberOfUses(AlgorithmUtil.numberOfUses(algorithmEntity))
                .addCuberNickname((algorithmEntity.getAddCuber().getNickname()))
                .situationName(algorithmEntity.getSituation().getName())
                .situationImage(algorithmEntity.getSituation().getImage())
                .methodName(algorithmEntity.getSituation().getMethod().getName())
                .build();
    }

    @Override
    public List<AlgorithmResponse> toResponse(List<AlgorithmEntity> algorithmEntities, CuberEntity cuber) {
        List<AlgorithmResponse> algorithmResponses = algorithmEntities.stream().map(algorithm -> toResponse(algorithm, cuber)).collect(Collectors.toList());
        SettingEntity defaultSortingSetting = settingService.getSetting(SettingConstants.DEFAULT_SORTING_SETTING_NAME, cuber);
        if (defaultSortingSetting.getValue().equals(SettingConstants.DEFAULT_SORTING_SETTING_VALUE_POPULARITY)) {
            AlgorithmsSortingUtil.sortByPopularity(algorithmResponses);
        } else {
            SettingEntity interceptionAsMoveSetting = settingService.getSetting(SettingConstants.INTERCEPTION_AS_MOVE_SETTING_NAME, cuber);
            SettingEntity doubleMoveAs2MovesSetting = settingService.getSetting(SettingConstants.DOUBLE_MOVE_AS_2_MOVES_SETTING_NAME, cuber);
            AlgorithmsSortingUtil.sortByLength(algorithmResponses, interceptionAsMoveSetting, doubleMoveAs2MovesSetting);
        }
        SettingEntity usingFirstSetting = settingService.getSetting(SettingConstants.SHOW_USING_ALGORITHMS_FIRST_SETTING_NAME, cuber);
        if (usingFirstSetting.getValue().equals(SettingConstants.SHOW_USING_ALGORITHMS_FIRST_SETTING_VALUE_TRUE))
            AlgorithmsSortingUtil.usingFirst(algorithmResponses);
        SettingEntity showUnverifiedAlgorithmsSetting = settingService.getSetting(SettingConstants.SHOW_UNVERIFIED_ALGORITHMS_SETTING_NAME, cuber);
        if (showUnverifiedAlgorithmsSetting.getValue().equals(SettingConstants.SHOW_UNVERIFIED_ALGORITHMS_SETTING_VALUE_FALSE))
            algorithmResponses = algorithmResponses.stream().filter(algorithmResponse -> algorithmResponse.isVerified() ||
                    algorithmResponse.getAddCuber() != null && algorithmResponse.getAddCuber().getId().equals(cuber.getId())).collect(Collectors.toList());
        return algorithmResponses;
    }

    @Override
    public List<AlgorithmApiResponse> toResponse(List<AlgorithmEntity> algorithmEntities) {
        return algorithmEntities.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
