package fertdt.alglearningv2.service;

import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.model.StatisticsEntity;

public interface StatisticsService {
    StatisticsEntity getTodayStatistics(CuberEntity cuber);

    void addNew(CuberEntity cuber);

    void addRepeat(CuberEntity cuber);

    void addForgot(CuberEntity cuber);

    void addTrained(CuberEntity cuber);
}
