package fertdt.alglearningv2.service;

import fertdt.alglearningv2.dto.response.WcaCompetitionResponse;

import java.util.List;

public interface WcaCompetitionService {
    void updateAllUpcomingCompetitions();

    List<WcaCompetitionResponse> getAllUpcomingCompetitions();

    List<WcaCompetitionResponse> getNearestCompetitions(String coordinates);
}
