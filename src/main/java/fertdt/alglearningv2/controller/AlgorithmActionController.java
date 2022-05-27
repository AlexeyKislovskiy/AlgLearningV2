package fertdt.alglearningv2.controller;

import fertdt.alglearningv2.exception.SameAlgorithmsAlreadyExistException;
import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.service.AlgorithmService;
import fertdt.alglearningv2.service.CuberService;
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
@RequestMapping("/AlgLearning/algorithms/algorithm")
public class AlgorithmActionController {
    private final AlgorithmService algorithmService;
    private final CuberService cuberService;

    @PostMapping("use/{algorithm-id}")
    public String use(@PathVariable("algorithm-id") UUID algorithmId) {
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        log.debug("User with nickname " + cuber.getNickname() + " change using status of algorithm with id " + algorithmId);
        algorithmService.use(algorithmId, cuber);
        UUID methodId = algorithmService.getMethodIdByAlgorithmId(algorithmId);
        return "redirect:/AlgLearning/api/method/" + methodId;
    }

    @PostMapping("add/{situation-id}/{text}")
    public String addAlgorithm(@PathVariable("situation-id") UUID situationId, @PathVariable("text") String text) {
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        log.debug("User with nickname " + cuber.getNickname() + " add algorithm " + text + " in situation with id " + situationId);
        try {
            UUID algorithmId = algorithmService.addAlgorithm(situationId, text, cuber);
            UUID methodId = algorithmService.getMethodIdByAlgorithmId(algorithmId);
            return "redirect:/AlgLearning/api/method/" + methodId;
        } catch (SameAlgorithmsAlreadyExistException e) {
            log.debug("Cannot add new algorithm: algorithm with this text for this situation already exist");
            return null;
        }
    }
}
