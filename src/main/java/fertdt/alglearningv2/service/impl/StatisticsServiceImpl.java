package fertdt.alglearningv2.service.impl;

import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.model.StatisticsEntity;
import fertdt.alglearningv2.repository.StatisticsRepository;
import fertdt.alglearningv2.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private final StatisticsRepository statisticsRepository;

    @Override
    public StatisticsEntity getTodayStatistics(CuberEntity cuber) {
        Optional<StatisticsEntity> statistics = statisticsRepository.findByDateAndCuber(LocalDate.now(), cuber);
        return statistics.orElseGet(() -> statisticsRepository.save(StatisticsEntity.builder()
                .cuber(cuber)
                .date(LocalDate.now())
                .numberOfForgot(0)
                .numberOfNew(0)
                .numberOfRepeat(0)
                .numberOfTrained(0)
                .build()));
    }

    @Override
    public void addNew(CuberEntity cuber) {
        StatisticsEntity statistics = getTodayStatistics(cuber);
        statistics.setNumberOfNew(statistics.getNumberOfNew() + 1);
        statisticsRepository.save(statistics);
    }

    @Override
    public void addRepeat(CuberEntity cuber) {
        StatisticsEntity statistics = getTodayStatistics(cuber);
        statistics.setNumberOfRepeat(statistics.getNumberOfRepeat() + 1);
        statisticsRepository.save(statistics);
    }

    @Override
    public void addForgot(CuberEntity cuber) {
        StatisticsEntity statistics = getTodayStatistics(cuber);
        statistics.setNumberOfForgot(statistics.getNumberOfForgot() + 1);
        statisticsRepository.save(statistics);
    }

    @Override
    public void addTrained(CuberEntity cuber) {
        StatisticsEntity statistics = getTodayStatistics(cuber);
        statistics.setNumberOfTrained(statistics.getNumberOfTrained() + 1);
        statisticsRepository.save(statistics);
    }
}
