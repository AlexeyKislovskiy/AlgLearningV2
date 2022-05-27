package fertdt.alglearningv2.controller;

import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.service.CuberService;
import fertdt.alglearningv2.service.MethodService;
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
@RequestMapping("/AlgLearning/algorithms/method")
public class MethodActionController {
    private final MethodService methodService;
    private final CuberService cuberService;
    private final SituationService situationService;

    @PostMapping("checkAll/{method-id}")
    public String checkAll(@PathVariable("method-id") UUID methodId) {
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        methodService.checkAll(methodId, cuber);
        log.debug("User with nickname " + cuber.getNickname() + " add all situations from method with id " + methodId + " into learned");
        return "redirect:/AlgLearning/api/method/" + methodId;
    }

    @PostMapping("plusAll/{method-id}")
    public String plusAll(@PathVariable("method-id") UUID methodId) {
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        methodService.plusAll(methodId, cuber);
        log.debug("User with nickname " + cuber.getNickname() + " add all situations from method with id " + methodId + " into learning");
        return "redirect:/AlgLearning/api/method/" + methodId;
    }

    @PostMapping("deleteAll/{method-id}")
    public String deleteAll(@PathVariable("method-id") UUID methodId) {
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        methodService.deleteAll(methodId, cuber);
        log.debug("User with nickname " + cuber.getNickname() + " add all situations from method with id " + methodId + " into not learning");
        return "redirect:/AlgLearning/api/method/" + methodId;
    }

    @PostMapping("nextTraining/{method-id}")
    public String nextTraining(@PathVariable("method-id") UUID methodId) {
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        situationService.train(cuber);
        log.debug("User with nickname " + cuber.getNickname() + " trained situation from method with id " + methodId);
        return "redirect:/AlgLearning/api/method/nextTraining/" + methodId;
    }

    @PostMapping("nextLearning/{difficulty}/{situation-id}")
    public String nextLearning(@PathVariable String difficulty, @PathVariable("situation-id") UUID situationId) {
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        UUID methodId = situationService.getMethodIdBySituationId(situationId);
        situationService.learn(situationId, difficulty, cuber);
        log.debug("User with nickname " + cuber.getNickname() + " learned situation from method with id " + methodId + " with difficulty " + difficulty);
        return "redirect:/AlgLearning/api/method/nextLearning/" + methodId;
    }
}
