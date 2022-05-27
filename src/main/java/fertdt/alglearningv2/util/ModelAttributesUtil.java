package fertdt.alglearningv2.util;

import fertdt.alglearningv2.dto.enums.AlgorithmUsingStatus;
import fertdt.alglearningv2.dto.enums.MethodLearningStatus;
import fertdt.alglearningv2.dto.enums.SituationLearningStatus;
import fertdt.alglearningv2.dto.enums.SituationTrainingStatus;
import fertdt.alglearningv2.model.CuberLearningSituationStatus;
import lombok.experimental.UtilityClass;
import org.springframework.ui.Model;

@UtilityClass
public class ModelAttributesUtil {
    public static void setDefaultAttributes(Model model) {
        model.addAttribute("methodNotLearningStatus", MethodLearningStatus.NOT_LEARNING);
        model.addAttribute("methodLearningStatus", MethodLearningStatus.LEARNING);
        model.addAttribute("methodLearnedStatus", MethodLearningStatus.LEARNED);
        model.addAttribute("situationNotLearningStatus", SituationLearningStatus.NOT_LEARNING);
        model.addAttribute("situationLearningStatus", SituationLearningStatus.LEARNING);
        model.addAttribute("situationLearnedStatus", SituationLearningStatus.LEARNED);
        model.addAttribute("situationNotTrainingStatus", SituationTrainingStatus.NOT_TRAINING);
        model.addAttribute("situationTrainingStatus", SituationTrainingStatus.TRAINING);
        model.addAttribute("algorithmNotUsingStatus", AlgorithmUsingStatus.NOT_USING);
        model.addAttribute("algorithmUsingStatus", AlgorithmUsingStatus.USING);
        model.addAttribute("learningNewStatus", CuberLearningSituationStatus.NEW);
        model.addAttribute("learningForgotStatus", CuberLearningSituationStatus.FORGOT);
        model.addAttribute("learningAwaitStatus", CuberLearningSituationStatus.AWAIT);
        model.addAttribute("learningRepeatStatus", CuberLearningSituationStatus.REPEAT);
    }
}
