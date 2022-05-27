package fertdt.alglearningv2.listener;

import fertdt.alglearningv2.service.WcaCompetitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartupListener {
    private final WcaCompetitionService wcaCompetitionService;

    @EventListener(ApplicationReadyEvent.class)
    public void updateWcaCompetitions() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            wcaCompetitionService.updateAllUpcomingCompetitions();
            log.info("Wca competitions information was updated");
        }, 0, 1, TimeUnit.HOURS);
    }
}
