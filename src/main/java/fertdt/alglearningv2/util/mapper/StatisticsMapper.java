package fertdt.alglearningv2.util.mapper;

import fertdt.alglearningv2.dto.response.StatisticsResponse;
import fertdt.alglearningv2.model.StatisticsEntity;

import java.util.List;

public interface StatisticsMapper {
    List<StatisticsResponse> toResponse(List<StatisticsEntity> statisticsEntities);
}
