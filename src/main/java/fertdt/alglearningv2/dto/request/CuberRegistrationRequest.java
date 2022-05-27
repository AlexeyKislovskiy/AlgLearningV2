package fertdt.alglearningv2.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CuberRegistrationRequest {
    private String nickname;

    private String email;

    private String password;

    private String confirmPassword;
}
