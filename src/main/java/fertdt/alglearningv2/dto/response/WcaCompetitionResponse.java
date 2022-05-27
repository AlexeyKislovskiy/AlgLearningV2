package fertdt.alglearningv2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WcaCompetitionResponse {
    private String name;

    private String link;

    private String coordinates;

    private String distance;

    private String distanceByCar;
}
