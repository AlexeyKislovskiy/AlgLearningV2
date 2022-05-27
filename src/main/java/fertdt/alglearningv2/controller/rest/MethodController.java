package fertdt.alglearningv2.controller.rest;

import fertdt.alglearningv2.dto.response.MethodResponse;
import fertdt.alglearningv2.dto.response.SituationWithScrambleResponse;
import fertdt.alglearningv2.exception.NoNextLearningSituationException;
import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.service.CuberService;
import fertdt.alglearningv2.service.MethodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/AlgLearning/api/method")
public class MethodController {
    private final MethodService methodService;
    private final CuberService cuberService;

    @GetMapping("/{method-id}")
    public MethodResponse getMethodById(@PathVariable("method-id") UUID methodId) {
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        MethodResponse methodResponse = methodService.getMethodById(methodId, cuber);
        log.debug("User with nickname " + cuber.getNickname() + " got situations from method  " + methodResponse.getName() + " with default sorting setting");
        return methodResponse;
    }

    @GetMapping("popularity/{method-id}")
    public MethodResponse getMethodByIdSortByPopularity(@PathVariable("method-id") UUID methodId) {
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        MethodResponse methodResponse = methodService.getMethodByIdSortByPopularity(methodId, cuber);
        log.debug("User with nickname " + cuber.getNickname() + " got situations from method  " + methodResponse.getName() + " with sorting by popularity");
        return methodResponse;
    }

    @GetMapping("length/{method-id}")
    public MethodResponse getMethodByIdSortByLength(@PathVariable("method-id") UUID methodId) {
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        MethodResponse methodResponse = methodService.getMethodByIdSortByLength(methodId, cuber);
        log.debug("User with nickname " + cuber.getNickname() + " got situations from method  " + methodResponse.getName() + " with sorting by length");
        return methodResponse;
    }

    @GetMapping("nextTraining/{method-id}")
    public SituationWithScrambleResponse getNextTrainingSituationInMethod(@PathVariable("method-id") UUID methodId) {
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        SituationWithScrambleResponse situationWithScramble = methodService.getNextTrainingSituationInMethod(methodId, cuber);
        log.trace("Next situation is " + situationWithScramble.getSituation().getName() + " with scramble: " + situationWithScramble.getScramble());
        return situationWithScramble;
    }

    @GetMapping("nextLearning/{method-id}")
    public SituationWithScrambleResponse getNextLearningSituationInMethod(@PathVariable("method-id") UUID methodId) {
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        try {
            SituationWithScrambleResponse situationWithScramble = methodService.getNextLearningSituationInMethod(methodId, cuber);
            log.trace("Next situation is " + situationWithScramble.getSituation().getName() + " with scramble: " + situationWithScramble.getScramble());
            return situationWithScramble;
        } catch (NoNextLearningSituationException e) {
            log.debug("There are no more situations to learn for this method, redirect to the main learning page");
            return null;
        }
    }
}
