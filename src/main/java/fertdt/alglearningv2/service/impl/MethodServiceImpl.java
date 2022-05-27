package fertdt.alglearningv2.service.impl;

import fertdt.alglearningv2.constants.SettingConstants;
import fertdt.alglearningv2.dto.enums.AlgorithmUsingStatus;
import fertdt.alglearningv2.dto.response.MethodResponse;
import fertdt.alglearningv2.dto.response.SituationResponse;
import fertdt.alglearningv2.dto.response.SituationWithScrambleResponse;
import fertdt.alglearningv2.exception.MethodNotFoundException;
import fertdt.alglearningv2.exception.NoNextLearningSituationException;
import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.model.MethodEntity;
import fertdt.alglearningv2.model.SettingEntity;
import fertdt.alglearningv2.model.SituationEntity;
import fertdt.alglearningv2.repository.MethodRepository;
import fertdt.alglearningv2.service.MethodService;
import fertdt.alglearningv2.service.ScrambleService;
import fertdt.alglearningv2.service.SettingService;
import fertdt.alglearningv2.service.SituationService;
import fertdt.alglearningv2.util.AlgorithmsSortingUtil;
import fertdt.alglearningv2.util.MethodUtil;
import fertdt.alglearningv2.util.mapper.MethodMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MethodServiceImpl implements MethodService {
    private final MethodRepository methodRepository;
    private final MethodMapper methodMapper;
    private final SituationService situationService;
    private final SettingService settingService;
    private final ScrambleService scrambleService;

    @Override
    public MethodResponse getMethodByName(String name, CuberEntity cuber) {
        return methodMapper.toResponse(methodRepository.findByName(name).orElseThrow(MethodNotFoundException::new), cuber);
    }

    @Override
    public MethodResponse getMethodById(UUID methodId, CuberEntity cuber) {
        return methodMapper.toResponse(methodRepository.findById(methodId).orElseThrow(MethodNotFoundException::new), cuber);
    }

    @Override
    public void checkAll(UUID methodId, CuberEntity cuber) {
        MethodEntity method = methodRepository.findById(methodId).orElseThrow(MethodNotFoundException::new);
        SettingEntity setting = settingService.getSetting(SettingConstants.CHECK_ALL_SETTING_NAME, cuber);
        Set<SituationEntity> situations;
        if (setting.getValue().equals(SettingConstants.CHECK_ALL_SETTING_VALUE_NOT_LEARNING))
            situations = MethodUtil.getNotLearningSituations(method, cuber);
        else if (setting.getValue().equals(SettingConstants.CHECK_ALL_SETTING_VALUE_LEARNING))
            situations = MethodUtil.getLearningSituations(method, cuber);
        else {
            situations = MethodUtil.getLearningSituations(method, cuber);
            situations.addAll(MethodUtil.getNotLearningSituations(method, cuber));
        }
        situations.forEach(situation -> situationService.check(situation.getId(), cuber));
    }

    @Override
    public void plusAll(UUID methodId, CuberEntity cuber) {
        MethodEntity method = methodRepository.findById(methodId).orElseThrow(MethodNotFoundException::new);
        SettingEntity setting = settingService.getSetting(SettingConstants.PLUS_ALL_SETTING_NAME, cuber);
        Set<SituationEntity> situations;
        if (setting.getValue().equals(SettingConstants.PLUS_ALL_SETTING_VALUE_LEARNED))
            situations = MethodUtil.getLearnedSituations(method, cuber);
        else if (setting.getValue().equals(SettingConstants.PLUS_ALL_SETTING_VALUE_NOT_LEARNING))
            situations = MethodUtil.getNotLearningSituations(method, cuber);
        else {
            situations = MethodUtil.getNotLearningSituations(method, cuber);
            situations.addAll(MethodUtil.getLearnedSituations(method, cuber));
        }
        situations.forEach(situation -> situationService.plus(situation.getId(), cuber));
    }

    @Override
    public void deleteAll(UUID methodId, CuberEntity cuber) {
        MethodEntity method = methodRepository.findById(methodId).orElseThrow(MethodNotFoundException::new);
        SettingEntity setting = settingService.getSetting(SettingConstants.DELETE_ALL_SETTING_NAME, cuber);
        Set<SituationEntity> situations;
        if (setting.getValue().equals(SettingConstants.DELETE_ALL_SETTING_VALUE_LEARNED))
            situations = MethodUtil.getLearnedSituations(method, cuber);
        else if (setting.getValue().equals(SettingConstants.DELETE_ALL_SETTING_VALUE_LEARNING))
            situations = MethodUtil.getLearningSituations(method, cuber);
        else {
            situations = MethodUtil.getLearningSituations(method, cuber);
            situations.addAll(MethodUtil.getLearnedSituations(method, cuber));
        }
        situations.forEach(situation -> situationService.delete(situation.getId(), cuber));
    }

    @Override
    public MethodResponse getMethodByIdSortByPopularity(UUID methodId, CuberEntity cuber) {
        MethodResponse methodResponse = getMethodById(methodId, cuber);
        for (SituationResponse situationResponse : methodResponse.getSituations()) {
            AlgorithmsSortingUtil.sortByPopularity(situationResponse.getAlgorithms());
            SettingEntity usingFirstSetting = settingService.getSetting(SettingConstants.SHOW_USING_ALGORITHMS_FIRST_SETTING_NAME, cuber);
            if (usingFirstSetting.getValue().equals(SettingConstants.SHOW_USING_ALGORITHMS_FIRST_SETTING_VALUE_TRUE))
                AlgorithmsSortingUtil.usingFirst(situationResponse.getAlgorithms());
        }
        return methodResponse;
    }

    @Override
    public MethodResponse getMethodByIdSortByLength(UUID methodId, CuberEntity cuber) {
        MethodResponse methodResponse = getMethodById(methodId, cuber);
        SettingEntity interceptionAsMoveSetting = settingService.getSetting(SettingConstants.INTERCEPTION_AS_MOVE_SETTING_NAME, cuber);
        SettingEntity doubleMoveAs2MovesSetting = settingService.getSetting(SettingConstants.DOUBLE_MOVE_AS_2_MOVES_SETTING_NAME, cuber);
        for (SituationResponse situationResponse : methodResponse.getSituations()) {
            AlgorithmsSortingUtil.sortByLength(situationResponse.getAlgorithms(), interceptionAsMoveSetting, doubleMoveAs2MovesSetting);
            SettingEntity usingFirstSetting = settingService.getSetting(SettingConstants.SHOW_USING_ALGORITHMS_FIRST_SETTING_NAME, cuber);
            if (usingFirstSetting.getValue().equals(SettingConstants.SHOW_USING_ALGORITHMS_FIRST_SETTING_VALUE_TRUE))
                AlgorithmsSortingUtil.usingFirst(situationResponse.getAlgorithms());
        }
        return methodResponse;
    }

    @Override
    public SituationWithScrambleResponse getNextTrainingSituationInMethod(UUID methodId, CuberEntity cuber) {
        MethodEntity method = methodRepository.findById(methodId).orElseThrow(MethodNotFoundException::new);
        List<SituationResponse> situations = situationService.getTrainingSituationsFromMethod(methodMapper.toResponse(method, cuber), cuber);
        return getRandomSituationFromList(situations, methodMapper.toResponse(method, cuber));
    }

    @Override
    public SituationWithScrambleResponse getNextLearningSituationInMethod(UUID methodId, CuberEntity cuber) {
        MethodEntity method = methodRepository.findById(methodId).orElseThrow(MethodNotFoundException::new);
        List<SituationResponse> situations = situationService.getLearningSituationsFromMethod(methodMapper.toResponse(method, cuber), cuber);
        if (situations.isEmpty()) throw new NoNextLearningSituationException();
        return getRandomSituationFromList(situations, methodMapper.toResponse(method, cuber));
    }

    private SituationWithScrambleResponse getRandomSituationFromList(List<SituationResponse> situations, MethodResponse method) {
        SituationResponse situation = situations.get(new Random().nextInt(situations.size()));
        situation.setAlgorithms(situation.getAlgorithms().stream().filter(algorithm -> algorithm.getUsingStatus().equals(AlgorithmUsingStatus.USING)).collect(Collectors.toList()));
        return SituationWithScrambleResponse.builder()
                .situation(situation)
                .scramble(scrambleService.getScramble(situation.getId()))
                .numberOfForgot(method.getNumberOfForgot())
                .numberOfNew(method.getNumberOfNew())
                .numberOfRepeat(method.getNumberOfRepeat())
                .build();
    }
}
