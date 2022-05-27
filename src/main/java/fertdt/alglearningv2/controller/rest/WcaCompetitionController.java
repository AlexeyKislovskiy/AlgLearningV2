package fertdt.alglearningv2.controller.rest;

import fertdt.alglearningv2.dto.response.WcaCompetitionResponse;
import fertdt.alglearningv2.service.WcaCompetitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("AlgLearning/wcaCompetition")
public class WcaCompetitionController {
    private final WcaCompetitionService wcaCompetitionService;

    @GetMapping("/{coordinates}")
    public List<WcaCompetitionResponse> test(@PathVariable String coordinates) {
        log.debug("Got nearest competitions from coordinates " + coordinates);
        return wcaCompetitionService.getNearestCompetitions(coordinates);
    }
}
