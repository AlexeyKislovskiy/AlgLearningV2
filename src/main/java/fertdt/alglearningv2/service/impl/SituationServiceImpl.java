package fertdt.alglearningv2.service.impl;

import fertdt.alglearningv2.constants.SettingConstants;
import fertdt.alglearningv2.dto.enums.SituationLearningStatus;
import fertdt.alglearningv2.dto.enums.SituationTrainingStatus;
import fertdt.alglearningv2.dto.response.MethodResponse;
import fertdt.alglearningv2.dto.response.SituationResponse;
import fertdt.alglearningv2.exception.SituationNotFoundException;
import fertdt.alglearningv2.exception.rest.SituationNotFoundApiException;
import fertdt.alglearningv2.model.*;
import fertdt.alglearningv2.repository.SituationRepository;
import fertdt.alglearningv2.repository.specification.SituationSpecification;
import fertdt.alglearningv2.service.*;
import fertdt.alglearningv2.util.mapper.SituationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SituationServiceImpl implements SituationService {
    private final SituationRepository situationRepository;
    private final SituationMapper situationMapper;
    private final CuberService cuberService;
    private final SettingService settingService;
    private final CuberLearningSituationService cuberLearningSituationService;
    private final StatisticsService statisticsService;

    @Override
    public SituationEntity getSituationEntityById(UUID situationId) {
        return situationRepository.findById(situationId).orElseThrow(SituationNotFoundException::new);
    }

    @Override
    public SituationEntity getSituationEntityByIdForApi(UUID situationId) {
        return situationRepository.findById(situationId).orElseThrow(SituationNotFoundApiException::new);
    }

    @Override
    public UUID getMethodIdBySituationId(UUID situationId) {
        SituationEntity situation = situationRepository.findById(situationId).orElseThrow(SituationNotFoundException::new);
        return situation.getMethod().getId();
    }

    @Override
    public void check(UUID situationId, CuberEntity cuber) {
        SituationEntity situation = situationRepository.findById(situationId).orElseThrow(SituationNotFoundException::new);
        CuberLearningSituationEntity cuberLearningSituationEntity = cuber.getLearningSituations().stream().
                filter(cuberLearningSituation -> cuberLearningSituation.getSituation().equals(situation)).
                reduce((first, second) -> first).orElse(null);
        cuber.getLearnedSituations().add(situation);
        cuber.getLearningSituations().removeIf(cuberLearningSituation -> cuberLearningSituation.getSituation().equals(situation));
        cuberService.save(cuber);
        if (cuberLearningSituationEntity != null) cuberLearningSituationService.delete(cuberLearningSituationEntity);
    }

    @Override
    public void plus(UUID situationId, CuberEntity cuber) {
        SituationEntity situation = situationRepository.findById(situationId).orElseThrow(SituationNotFoundException::new);
        if (cuber.getLearningSituations().stream().map(CuberLearningSituationEntity::getSituation).collect(Collectors.toSet()).contains(situation))
            return;
        SettingEntity firstInterval = settingService.getSetting(SettingConstants.FIRST_INTERVAL_SETTING_NAME, cuber);
        cuber.getLearnedSituations().remove(situation);
        cuberLearningSituationService.save(CuberLearningSituationEntity.builder()
                .situation(situation)
                .cuber(cuber)
                .lastInterval(Double.parseDouble(firstInterval.getValue()))
                .nextRepeat(LocalDate.now())
                .status(CuberLearningSituationStatus.NEW)
                .build());
    }

    @Override
    public void delete(UUID situationId, CuberEntity cuber) {
        SituationEntity situation = situationRepository.findById(situationId).orElseThrow(SituationNotFoundException::new);
        CuberLearningSituationEntity cuberLearningSituationEntity = cuber.getLearningSituations().stream().
                filter(cuberLearningSituation -> cuberLearningSituation.getSituation().equals(situation)).
                reduce((first, second) -> first).orElse(null);
        cuber.getLearnedSituations().remove(situation);
        cuber.getLearningSituations().removeIf(cuberLearningSituation -> cuberLearningSituation.getSituation().equals(situation));
        cuberService.save(cuber);
        if (cuberLearningSituationEntity != null) cuberLearningSituationService.delete(cuberLearningSituationEntity);
    }

    @Override
    public List<SituationResponse> getSituationsByMethodIdAndSearchExpression(UUID methodId, String searchExpression, CuberEntity cuber) {
        return situationMapper.toResponse(situationRepository.findAll(SituationSpecification.searchExpression(searchExpression, methodId)), cuber);
    }

    @Override
    public List<SituationResponse> getTrainingSituationsFromMethod(MethodResponse methodResponse, CuberEntity cuber) {
        List<SituationResponse> trainingSituations = methodResponse.getSituations().stream().filter(situation ->
                situation.getTrainingStatus().equals(SituationTrainingStatus.TRAINING)).collect(Collectors.toList());
        if (trainingSituations.isEmpty()) {
            SettingEntity notLearning = settingService.getSetting(SettingConstants.DEFAULT_INCLUDE_NOT_LEARNING_IN_TRAINING_SETTING_NAME, cuber);
            SettingEntity learning = settingService.getSetting(SettingConstants.DEFAULT_INCLUDE_LEARNING_IN_TRAINING_SETTING_NAME, cuber);
            SettingEntity learned = settingService.getSetting(SettingConstants.DEFAULT_INCLUDE_LEARNED_IN_TRAINING_SETTING_NAME, cuber);
            if (notLearning.getValue().equals(SettingConstants.DEFAULT_INCLUDE_NOT_LEARNING_IN_TRAINING_SETTING_VALUE_TRUE)) {
                trainingSituations.addAll(methodResponse.getSituations().stream().filter(situation ->
                        situation.getLearningStatus().equals(SituationLearningStatus.NOT_LEARNING)).collect(Collectors.toList()));
            }
            if (learning.getValue().equals(SettingConstants.DEFAULT_INCLUDE_LEARNING_IN_TRAINING_SETTING_VALUE_TRUE)) {
                trainingSituations.addAll(methodResponse.getSituations().stream().filter(situation ->
                        situation.getLearningStatus().equals(SituationLearningStatus.LEARNING)).collect(Collectors.toList()));
            }
            if (learned.getValue().equals(SettingConstants.DEFAULT_INCLUDE_LEARNED_IN_TRAINING_SETTING_VALUE_TRUE)) {
                trainingSituations.addAll(methodResponse.getSituations().stream().filter(situation ->
                        situation.getLearningStatus().equals(SituationLearningStatus.LEARNED)).collect(Collectors.toList()));
            }
            if (trainingSituations.isEmpty()) trainingSituations = methodResponse.getSituations();
            List<SituationEntity> situationEntities = trainingSituations.stream().map(situationResponse ->
                    situationRepository.findById(situationResponse.getId()).get()).collect(Collectors.toList());
            cuber.getTrainingSituations().addAll(situationEntities);
            cuberService.save(cuber);
            trainingSituations.forEach(trainingSituation -> trainingSituation.setTrainingStatus(SituationTrainingStatus.TRAINING));
        }
        return trainingSituations;
    }

    @Override
    public void changeTrainingSituation(UUID situationId, CuberEntity cuber) {
        SituationEntity situation = situationRepository.findById(situationId).orElseThrow(SituationNotFoundException::new);
        if (cuber.getTrainingSituations().contains(situation)) cuber.getTrainingSituations().remove(situation);
        else cuber.getTrainingSituations().add(situation);
        cuberService.save(cuber);
    }

    @Override
    public List<SituationResponse> getLearningSituationsFromMethod(MethodResponse methodResponse, CuberEntity cuber) {
        return methodResponse.getSituations().stream().filter(situation -> situation.getLearningStatus().equals(SituationLearningStatus.LEARNING)
                && !situation.getCuberLearningSituationStatus().equals(CuberLearningSituationStatus.AWAIT)).collect(Collectors.toList());
    }

    @Override
    public void learn(UUID situationId, String difficulty, CuberEntity cuber) {
        SituationEntity situation = situationRepository.findById(situationId).orElseThrow(SituationNotFoundException::new);
        CuberLearningSituationEntity cuberLearningSituation = cuber.getLearningSituations().stream()
                .filter(learningSituation -> learningSituation.getSituation().equals(situation)).reduce((first, second) -> first)
                .orElseThrow(SituationNotFoundException::new);
        Double multiplier;
        CuberLearningSituationStatus status;
        if (cuberLearningSituation.getStatus().equals(CuberLearningSituationStatus.REPEAT)) {
            status = CuberLearningSituationStatus.AWAIT;
            if (!difficulty.equals("again")) statisticsService.addRepeat(cuber);
        } else if (cuberLearningSituation.getStatus().equals(CuberLearningSituationStatus.NEW)) {
            status = CuberLearningSituationStatus.REPEAT;
            if (!difficulty.equals("again")) statisticsService.addNew(cuber);
        } else {
            status = CuberLearningSituationStatus.REPEAT;
            if (!difficulty.equals("again")) statisticsService.addForgot(cuber);
        }
        switch (difficulty) {
            case "easy":
                multiplier = Double.parseDouble(settingService.getSetting(SettingConstants.EASY_MULTIPLIER_SETTING_NAME, cuber).getValue());
                break;
            case "medium":
                multiplier = Double.parseDouble(settingService.getSetting(SettingConstants.MEDIUM_MULTIPLIER_SETTING_NAME, cuber).getValue());
                break;
            case "hard":
                multiplier = Double.parseDouble(settingService.getSetting(SettingConstants.HARD_MULTIPLIER_SETTING_NAME, cuber).getValue());
                break;
            default:
                status = CuberLearningSituationStatus.FORGOT;
                if (settingService.getSetting(SettingConstants.RESET_WHEN_FORGOT_SETTING_NAME, cuber).getValue().equals(SettingConstants.RESET_WHEN_FORGOT_SETTING_VALUE_TRUE))
                    multiplier = 0d;
                else
                    multiplier = Double.parseDouble(settingService.getSetting(SettingConstants.AGAIN_MULTIPLIER_SETTING_NAME, cuber).getValue());
                break;
        }
        Double lastInterval = cuberLearningSituation.getLastInterval() * multiplier;
        if (lastInterval < 1d) lastInterval = 1d;
        if (cuberLearningSituation.getStatus().equals(CuberLearningSituationStatus.REPEAT)) {
            cuberLearningSituation.setNextRepeat(LocalDate.now().plusDays(lastInterval.longValue()));
            cuberLearningSituation.setLastInterval(lastInterval);
        }
        cuberLearningSituation.setStatus(status);
        cuberLearningSituationService.save(cuberLearningSituation);
        cuberService.save(cuber);
    }

    @Override
    public void train(CuberEntity cuber) {
        statisticsService.addTrained(cuber);
    }

}
