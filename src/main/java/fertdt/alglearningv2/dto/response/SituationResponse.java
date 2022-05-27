package fertdt.alglearningv2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class SituationResponse extends SituationSimpleResponse {
    private SituationSimpleResponse mirror;

    private SituationSimpleResponse reverse;

    private SituationSimpleResponse mirrorReverse;
}
