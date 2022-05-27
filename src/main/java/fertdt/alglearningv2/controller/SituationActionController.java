package fertdt.alglearningv2.controller;

import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.service.CuberService;
import fertdt.alglearningv2.service.SituationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/AlgLearning/algorithms/situation")
public class SituationActionController {
    private final SituationService situationService;
    private final CuberService cuberService;

    @PostMapping("check/{situation-id}")
    public String check(@PathVariable("situation-id") UUID situationId) {
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        situationService.check(situationId, cuber);
        UUID methodId = situationService.getMethodIdBySituationId(situationId);
        log.debug("User with nickname " + cuber.getNickname() + " add situation with id " + situationId + " into learned");
        return "redirect:/AlgLearning/api/method/" + methodId;
    }

    @PostMapping("plus/{situation-id}")
    public String plus(@PathVariable("situation-id") UUID situationId) {
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        situationService.plus(situationId, cuber);
        UUID methodId = situationService.getMethodIdBySituationId(situationId);
        log.debug("User with nickname " + cuber.getNickname() + " add situation with id " + situationId + " into learning");
        return "redirect:/AlgLearning/api/method/" + methodId;
    }

    @PostMapping("delete/{situation-id}")
    public String delete(@PathVariable("situation-id") UUID situationId) {
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        situationService.delete(situationId, cuber);
        UUID methodId = situationService.getMethodIdBySituationId(situationId);
        log.debug("User with nickname " + cuber.getNickname() + " add situation with id " + situationId + " into not learning");
        return "redirect:/AlgLearning/api/method/" + methodId;
    }

    @PostMapping("train/{situation-id}")
    public void train(@PathVariable("situation-id") UUID situationId) {
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        situationService.changeTrainingSituation(situationId, cuber);
        log.debug("User with nickname " + cuber.getNickname() + " change training status of situation with id " + situationId);
    }
}
