package fertdt.alglearningv2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlgorithmApiResponse {
    private UUID id;

    private String text;

    private boolean verified;

    private Integer numberOfUses;

    private String addCuberNickname;

    private String situationName;

    private String situationImage;

    private String methodName;
}
