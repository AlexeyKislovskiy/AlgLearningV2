package fertdt.alglearningv2.controller.rest;

import fertdt.alglearningv2.dto.request.AlgorithmRequest;
import fertdt.alglearningv2.dto.response.AlgorithmApiResponse;
import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.service.AlgorithmService;
import fertdt.alglearningv2.service.CuberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/AlgLearning/api/algorithm")
public class AlgorithmController {
    private final AlgorithmService algorithmService;
    private final CuberService cuberService;

    @GetMapping("/unverified")
    public List<AlgorithmApiResponse> getAllUnverifiedAlgorithms() {
        return algorithmService.getAllUnverifiedAlgorithms();
    }

    @GetMapping("/{algorithm-id}")
    public ResponseEntity<?> getAlgorithmById(@PathVariable("algorithm-id") UUID algorithmId) {
        ResponseEntity<?> response = ResponseEntity.status(HttpStatus.OK).body(algorithmService.getAlgorithmById(algorithmId));
        log.debug("Got algorithm with id " + algorithmId + " throw rest controller");
        return response;
    }

    @PostMapping("")
    public ResponseEntity<?> addNewAlgorithm(@RequestBody AlgorithmRequest algorithmRequest) {
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        ResponseEntity<?> response = ResponseEntity.status(HttpStatus.CREATED).body(algorithmService.addAlgorithm(algorithmRequest, cuber));
        log.debug("User with nickname " + cuber.getNickname() + " added new algorithm throw rest controller: " + algorithmRequest);
        return response;
    }

    @DeleteMapping("/{algorithm-id}")
    public ResponseEntity<?> deleteAlgorithm(@PathVariable("algorithm-id") UUID algorithmId) {
        ResponseEntity<?> response = ResponseEntity.status(HttpStatus.OK).body(algorithmService.deleteAlgorithm(algorithmId));
        log.debug("Algorithm with id " + algorithmId + " was deleted throw rest controller");
        return response;
    }
}
