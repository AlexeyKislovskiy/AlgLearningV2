package fertdt.alglearningv2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SituationWithScrambleResponse {
    SituationResponse situation;

    String scramble;

    Integer numberOfNew;

    Integer numberOfForgot;

    Integer numberOfRepeat;
}
