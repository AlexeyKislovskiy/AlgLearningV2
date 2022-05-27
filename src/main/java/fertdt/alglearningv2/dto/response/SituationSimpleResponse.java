package fertdt.alglearningv2.dto.response;

import fertdt.alglearningv2.dto.enums.SituationLearningStatus;
import fertdt.alglearningv2.dto.enums.SituationTrainingStatus;
import fertdt.alglearningv2.model.CuberLearningSituationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class SituationSimpleResponse {
    private UUID id;

    private String name;

    private String image;

    private List<AlgorithmResponse> algorithms;

    private SituationLearningStatus learningStatus;

    private SituationTrainingStatus trainingStatus;

    private CuberLearningSituationStatus cuberLearningSituationStatus;
}
