package fertdt.alglearningv2.dto.response;

import fertdt.alglearningv2.dto.enums.AlgorithmUsingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlgorithmResponse {
    private UUID id;

    private String text;

    private boolean verified;

    private Integer numberOfUses;

    private AlgorithmUsingStatus usingStatus;

    private CuberResponse addCuber;
}
