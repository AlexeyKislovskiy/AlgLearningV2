package fertdt.alglearningv2.util;

import fertdt.alglearningv2.dto.enums.MethodLearningStatus;
import fertdt.alglearningv2.model.*;
import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class MethodUtil {
    public int numberOfSituations(MethodEntity method) {
        return method == null || method.getSituations() == null ? 0 : method.getSituations().size();
    }

    public static boolean isLearned(MethodEntity method, CuberEntity cuber) {
        return method.getSituations().size() == numberOfLearnedSituations(method, cuber);
    }

    public static boolean isLearning(MethodEntity method, CuberEntity cuber) {
        return !isLearned(method, cuber) && numberOfNotLearningSituations(method, cuber) < method.getSituations().size();
    }

    public static MethodLearningStatus getMethodLearningStatus(MethodEntity method, CuberEntity cuber) {
        if (isLearned(method, cuber)) return MethodLearningStatus.LEARNED;
        else if (isLearning(method, cuber)) return MethodLearningStatus.LEARNING;
        else return MethodLearningStatus.NOT_LEARNING;
    }

    public static int numberOfLearned(MethodEntity method) {
        List<SituationEntity> situations = method.getSituations();
        Set<CuberEntity> cubers = new HashSet<>();
        for (SituationEntity situation : situations) {
            cubers.addAll(situation.getLearned());
        }
        return (int) cubers.stream().filter(cuber -> isLearned(method, cuber)).count();
    }

    public static int numberOfLearning(MethodEntity method) {
        List<SituationEntity> situations = method.getSituations();
        Set<CuberEntity> cubers = new HashSet<>();
        for (SituationEntity situation : situations) {
            cubers.addAll(situation.getLearned());
            cubers.addAll(situation.getLearning().stream().map(CuberLearningSituationEntity::getCuber).collect(Collectors.toSet()));
        }
        return (int) cubers.stream().filter(cuber -> isLearning(method, cuber)).count();
    }

    public static int numberOfLearnedSituations(MethodEntity method, CuberEntity cuber) {
        return getLearnedSituations(method, cuber).size();
    }

    public static int numberOfLearningSituations(MethodEntity method, CuberEntity cuber) {
        return getLearningSituations(method, cuber).size();
    }

    public static int numberOfNotLearningSituations(MethodEntity method, CuberEntity cuber) {
        return method.getSituations().size() - numberOfLearningSituations(method, cuber) - numberOfLearnedSituations(method, cuber);
    }

    public static Set<SituationEntity> getLearnedSituations(MethodEntity method, CuberEntity cuber) {
        List<SituationEntity> allSituations = method.getSituations();
        Set<SituationEntity> learnedSituations = cuber.getLearnedSituations();
        return allSituations.stream().filter(learnedSituations::contains).collect(Collectors.toSet());
    }

    public static Set<SituationEntity> getLearningSituations(MethodEntity method, CuberEntity cuber) {
        List<SituationEntity> allSituations = method.getSituations();
        Set<SituationEntity> learningSituations = cuber.getLearningSituations().stream().map(CuberLearningSituationEntity::getSituation).collect(Collectors.toSet());
        return allSituations.stream().filter(learningSituations::contains).collect(Collectors.toSet());
    }

    public static Set<SituationEntity> getNotLearningSituations(MethodEntity method, CuberEntity cuber) {
        List<SituationEntity> allSituations = method.getSituations();
        Set<SituationEntity> learningSituations = getLearningSituations(method, cuber);
        Set<SituationEntity> learnedSituations = getLearnedSituations(method, cuber);
        Set<SituationEntity> notLearningSituations = new HashSet<>();
        for (SituationEntity situation : allSituations) {
            if (!learningSituations.contains(situation) && !learnedSituations.contains(situation))
                notLearningSituations.add(situation);
        }
        return notLearningSituations;
    }

    public static Integer getNumberOfNew(MethodEntity method, CuberEntity cuber) {
        return Math.toIntExact(cuber.getLearningSituations().stream().filter(learningSituation -> method.getSituations()
                .contains(learningSituation.getSituation())).filter(learningSituation ->
                learningSituation.getStatus().equals(CuberLearningSituationStatus.NEW)).count());
    }

    public static Integer getNumberOfForgot(MethodEntity method, CuberEntity cuber) {
        return Math.toIntExact(cuber.getLearningSituations().stream().filter(learningSituation -> method.getSituations()
                .contains(learningSituation.getSituation())).filter(learningSituation ->
                learningSituation.getStatus().equals(CuberLearningSituationStatus.FORGOT)).count());
    }

    public static Integer getNumberOfRepeat(MethodEntity method, CuberEntity cuber) {
        return Math.toIntExact(cuber.getLearningSituations().stream().filter(learningSituation -> method.getSituations()
                .contains(learningSituation.getSituation())).filter(learningSituation ->
                learningSituation.getStatus().equals(CuberLearningSituationStatus.REPEAT)).count());
    }
}
