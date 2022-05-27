package fertdt.alglearningv2.controller.rest;

import fertdt.alglearningv2.dto.response.SituationResponse;
import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.service.CuberService;
import fertdt.alglearningv2.service.SituationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/AlgLearning/api/situation")
public class SituationController {
    private final SituationService situationService;
    private final CuberService cuberService;

    @GetMapping("search/{method-id}/{search-expression}")
    public List<SituationResponse> getSituationsByMethodIdAndSearchExpression(@PathVariable("method-id") UUID methodId,
                                                                              @PathVariable("search-expression") String searchExpression) {
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        List<SituationResponse> situationResponses = situationService.getSituationsByMethodIdAndSearchExpression(methodId, searchExpression, cuber);
        log.debug("User with nickname " + cuber.getNickname() + " got situations from method with id " + methodId + " with search expression " + searchExpression);
        log.trace("Total number of situations: " + situationResponses.size());
        return situationResponses;
    }

    @GetMapping("search/{method-id}")
    public List<SituationResponse> getSituationsByMethodIdAndSearchExpression(@PathVariable("method-id") UUID methodId) {
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        List<SituationResponse> situationResponses = situationService.getSituationsByMethodIdAndSearchExpression(methodId, "", cuber);
        log.debug("User with nickname " + cuber.getNickname() + " got situations from method with id " + methodId + " with empty search expression");
        log.trace("Total number of situations: " + situationResponses.size());
        return situationResponses;
    }
}

