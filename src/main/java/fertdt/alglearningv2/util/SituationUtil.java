package fertdt.alglearningv2.util;

import fertdt.alglearningv2.dto.enums.SituationLearningStatus;
import fertdt.alglearningv2.dto.enums.SituationTrainingStatus;
import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.model.CuberLearningSituationEntity;
import fertdt.alglearningv2.model.CuberLearningSituationStatus;
import fertdt.alglearningv2.model.SituationEntity;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

@UtilityClass
public class SituationUtil {
    public static SituationLearningStatus getSituationLearningStatus(SituationEntity situation, CuberEntity cuber) {
        if (cuber.getLearnedSituations().contains(situation)) return SituationLearningStatus.LEARNED;
        else if (cuber.getLearningSituations().stream().map(CuberLearningSituationEntity::getSituation)
                .collect(Collectors.toList()).contains(situation)) return SituationLearningStatus.LEARNING;
        else return SituationLearningStatus.NOT_LEARNING;
    }

    public static SituationTrainingStatus getSituationTrainingStatus(SituationEntity situation, CuberEntity cuber) {
        if (cuber.getTrainingSituations().contains(situation)) return SituationTrainingStatus.TRAINING;
        else return SituationTrainingStatus.NOT_TRAINING;
    }

    public static CuberLearningSituationStatus getCuberLearningSituationStatus(SituationEntity situation, CuberEntity cuber) {
        CuberLearningSituationEntity cuberLearningSituation = cuber.getLearningSituations().stream()
                .filter(learningSituation -> learningSituation.getSituation().equals(situation)).reduce((first, second) -> first).orElse(null);
        if (cuberLearningSituation == null) return null;
        else return cuberLearningSituation.getStatus();
    }

}
