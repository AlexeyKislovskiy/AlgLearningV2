package fertdt.alglearningv2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiExceptionResponse {
    private Integer status;

    private String error;

    private String message;
}
