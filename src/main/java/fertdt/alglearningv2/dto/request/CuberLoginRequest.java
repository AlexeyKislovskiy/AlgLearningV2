package fertdt.alglearningv2.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CuberLoginRequest {
    private String nickname;

    private String password;

    private boolean rememberMe;
}
