package fertdt.alglearningv2.controller;

import fertdt.alglearningv2.constants.SettingConstants;
import fertdt.alglearningv2.dto.response.AlgorithmApiResponse;
import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.service.AlgorithmService;
import fertdt.alglearningv2.service.CuberService;
import fertdt.alglearningv2.service.RoleService;
import fertdt.alglearningv2.service.SettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/AlgLearning/moderator")
public class ModeratorController {
    private final CuberService cuberService;
    private final SettingService settingService;
    private final AlgorithmService algorithmService;
    private final RoleService roleService;

    @GetMapping
    public String getModeratorPage(Model model) {
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        cuberService.updateLastVisited(cuber);
        model.addAttribute("background", settingService.getSetting(SettingConstants.BACKGROUND_SETTING_NAME, cuber).getValue());
        model.addAttribute("moderator", cuber.getAuthorities().contains(roleService.getRoleByName("ROLE_MODERATOR")));
        log.debug("User with nickname " + cuber.getNickname() + " got moderator page");
        List<AlgorithmApiResponse> unverifiedAlgorithms = algorithmService.getAllUnverifiedAlgorithms();
        log.trace("Number of unverified algorithms: " + unverifiedAlgorithms.size());
        model.addAttribute("algorithms", unverifiedAlgorithms);
        return "moderator";
    }

    @PostMapping("/verify/{algorithm-id}")
    public String verify(@PathVariable("algorithm-id") UUID algorithmId) {
        algorithmService.verify(algorithmId);
        log.debug("Algorithm with id " + algorithmId + " was verified");
        return "redirect:/AlgLearning/api/algorithm/unverified";
    }

    @PostMapping("/notVerify/{algorithm-id}")
    public String notVerify(@PathVariable("algorithm-id") UUID algorithmId) {
        algorithmService.notVerify(algorithmId);
        log.debug("Algorithm with id " + algorithmId + " was not verified and was deleted");
        return "redirect:/AlgLearning/api/algorithm/unverified";
    }
}
