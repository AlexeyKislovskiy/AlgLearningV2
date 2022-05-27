package fertdt.alglearningv2.dto.response;

import fertdt.alglearningv2.dto.enums.MethodLearningStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MethodResponse {
    private UUID id;

    private String name;

    private String description;

    private String image;

    private List<SituationResponse> situations;

    private MethodLearningStatus learningStatus;

    private Integer numberOfSituations;

    private Integer numberOfLearning;

    private Integer numberOfLearned;

    private Integer numberOfNotLearningSituations;

    private Integer numberOfLearningSituations;

    private Integer numberOfLearnedSituations;

    private Integer numberOfNew;

    private Integer numberOfForgot;

    private Integer numberOfRepeat;
}
