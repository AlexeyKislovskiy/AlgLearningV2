package fertdt.alglearningv2.util.mapper.impl;

import fertdt.alglearningv2.dto.response.StatisticsResponse;
import fertdt.alglearningv2.model.StatisticsEntity;
import fertdt.alglearningv2.util.mapper.StatisticsMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StatisticsMapperImpl implements StatisticsMapper {
    @Override
    public List<StatisticsResponse> toResponse(List<StatisticsEntity> statisticsEntities) {
        List<StatisticsResponse> statisticsResponses = new ArrayList<>();
        List<StatisticsEntity> lastDay = statisticsEntities.stream().
                filter(statistics -> statistics.getDate().isEqual(LocalDate.now())).collect(Collectors.toList());
        List<StatisticsEntity> lastWeek = statisticsEntities.stream().
                filter(statistics -> statistics.getDate().isAfter(LocalDate.now().minus(1, ChronoUnit.WEEKS))).collect(Collectors.toList());
        List<StatisticsEntity> lastMonth = statisticsEntities.stream().
                filter(statistics -> statistics.getDate().isAfter(LocalDate.now().minus(1, ChronoUnit.MONTHS))).collect(Collectors.toList());
        List<StatisticsEntity> lastYear = statisticsEntities.stream().
                filter(statistics -> statistics.getDate().isAfter(LocalDate.now().minus(1, ChronoUnit.YEARS))).collect(Collectors.toList());
        statisticsResponses.add(getResponse("За последний день", lastDay));
        statisticsResponses.add(getResponse("За последнюю неделю", lastWeek));
        statisticsResponses.add(getResponse("За последний месяц", lastMonth));
        statisticsResponses.add(getResponse("За последний год", lastYear));
        statisticsResponses.add(getResponse("За все время", statisticsEntities));
        return statisticsResponses;
    }

    private StatisticsResponse getResponse(String name, List<StatisticsEntity> statisticsEntities) {
        return StatisticsResponse.builder()
                .name(name)
                .numberOfForgot(statisticsEntities.stream().mapToInt(StatisticsEntity::getNumberOfForgot).sum())
                .numberOfNew(statisticsEntities.stream().mapToInt(StatisticsEntity::getNumberOfNew).sum())
                .numberOfRepeat(statisticsEntities.stream().mapToInt(StatisticsEntity::getNumberOfRepeat).sum())
                .numberOfTrained(statisticsEntities.stream().mapToInt(StatisticsEntity::getNumberOfTrained).sum())
                .build();
    }
}
