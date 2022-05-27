package fertdt.alglearningv2.dto.response;

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
public class CuberResponse {
    private UUID id;

    private String nickname;

    private String email;

    private String registrationDate;

    private Integer visitedDays;

    private Integer visitedDaysRow;

    private Integer numberOfLearning;

    private Integer numberOfLearned;

    private List<StatisticsResponse> statistics;
}
