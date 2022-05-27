package fertdt.alglearningv2.controller;

import fertdt.alglearningv2.constants.SettingConstants;
import fertdt.alglearningv2.dto.response.CubeResponse;
import fertdt.alglearningv2.dto.response.MethodResponse;
import fertdt.alglearningv2.dto.response.SituationWithScrambleResponse;
import fertdt.alglearningv2.exception.NoNextLearningSituationException;
import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.service.*;
import fertdt.alglearningv2.util.ModelAttributesUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/AlgLearning/learning")
public class LearningController {
    private final CuberService cuberService;
    private final CubeService cubeService;
    private final MethodService methodService;
    private final SettingService settingService;
    private final CuberLearningSituationService cuberLearningSituationService;
    private final RoleService roleService;

    @GetMapping
    public String getBasicLearningPage(Model model, @RequestParam(required = false, name = "cube") CubeResponse cube) {
        ModelAttributesUtil.setDefaultAttributes(model);
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        cuberService.updateLastVisited(cuber);
        model.addAttribute("background", settingService.getSetting(SettingConstants.BACKGROUND_SETTING_NAME, cuber).getValue());
        model.addAttribute("moderator", cuber.getAuthorities().contains(roleService.getRoleByName("ROLE_MODERATOR")));
        log.debug("User with nickname " + cuber.getNickname() + " got learning page");
        cuberLearningSituationService.updateAllStatus(cuber);
        List<CubeResponse> cubes = cubeService.getAllCubes(cuber);
        model.addAttribute("cubes", cubes);
        List<MethodResponse> methods;
        if (cube == null) {
            methods = cubes.stream().flatMap(cubeResponse -> cubeResponse.getMethods().stream())
                    .filter(method -> method.getNumberOfNew() > 0 || method.getNumberOfForgot() > 0 || method.getNumberOfRepeat() > 0)
                    .collect(Collectors.toList());
            model.addAttribute("methods", methods);
            log.trace("A specific cube is not selected or there is no cube with this name, methods for all cubes will be shown");
        } else {
            methods = cube.getMethods().stream()
                    .filter(method -> method.getNumberOfNew() > 0 || method.getNumberOfForgot() > 0 || method.getNumberOfRepeat() > 0)
                    .collect(Collectors.toList());
            model.addAttribute("methods", methods);
            model.addAttribute("selectedCube", cube);
            log.trace("Selected cube: " + cube.getName());
        }
        log.trace("Available methods for learning: " + methods.size());
        return "learning";
    }

    @GetMapping("/method/{method-name}")
    public String getLearningPageForMethod(Model model, @PathVariable(value = "method-name", required = false) MethodResponse method) {
        if (method == null) {
            log.debug("A specific method is not selected or there is no method with this name, redirect to the main algorithms page");
            return "redirect:/AlgLearning/learning";
        }
        ModelAttributesUtil.setDefaultAttributes(model);
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        cuberService.updateLastVisited(cuber);
        model.addAttribute("background", settingService.getSetting(SettingConstants.BACKGROUND_SETTING_NAME, cuber).getValue());
        model.addAttribute("moderator", cuber.getAuthorities().contains(roleService.getRoleByName("ROLE_MODERATOR")));
        log.debug("User with nickname " + cuber.getNickname() + " got learning page for " + method.getName());
        try {
            SituationWithScrambleResponse situationWithScramble = methodService.getNextLearningSituationInMethod(method.getId(), cuber);
            model.addAttribute("method", method);
            model.addAttribute("situation", situationWithScramble.getSituation());
            model.addAttribute("scramble", situationWithScramble.getScramble());
            model.addAttribute("hideNumberOfAlgorithmsInLearning", settingService.getSetting(SettingConstants.HIDE_NUMBER_OF_ALGORITHMS_IN_LEARNING_SETTING_NAME, cuber).getValue());
            model.addAttribute("hideSituationInLearning", settingService.getSetting(SettingConstants.HIDE_SITUATION_IN_LEARNING_SETTING_NAME, cuber).getValue());
            log.trace("Hide situation in learning? " + model.getAttribute("hideSituationInLearning"));
            log.trace("Hide numbers of algorithms in learning? " + model.getAttribute("hideNumberOfAlgorithmsInLearning"));
            log.trace("Next situation is " + situationWithScramble.getSituation().getName() + " with scramble: " + situationWithScramble.getScramble());
            return "method-learning";
        } catch (NoNextLearningSituationException e) {
            log.debug("There are no more situations to learn for this method, redirect to the main learning page");
            return "redirect:/AlgLearning/learning";
        }
    }
}
