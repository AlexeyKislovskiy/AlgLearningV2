package fertdt.alglearningv2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CubeResponse {
    private UUID id;

    private String name;

    private String image;

    private String description;

    private List<MethodResponse> methods;
}
