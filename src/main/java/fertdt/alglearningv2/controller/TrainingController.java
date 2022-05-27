package fertdt.alglearningv2.controller;

import fertdt.alglearningv2.constants.SettingConstants;
import fertdt.alglearningv2.dto.response.CubeResponse;
import fertdt.alglearningv2.dto.response.MethodResponse;
import fertdt.alglearningv2.dto.response.SituationResponse;
import fertdt.alglearningv2.dto.response.SituationWithScrambleResponse;
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
@RequestMapping("/AlgLearning/training")
public class TrainingController {
    private final CuberService cuberService;
    private final CubeService cubeService;
    private final MethodService methodService;
    private final SettingService settingService;
    private final SituationService situationService;
    private final RoleService roleService;

    @GetMapping
    public String getBasicTrainingPage(Model model, @RequestParam(required = false, name = "cube") CubeResponse cube) {
        ModelAttributesUtil.setDefaultAttributes(model);
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        cuberService.updateLastVisited(cuber);
        model.addAttribute("background", settingService.getSetting(SettingConstants.BACKGROUND_SETTING_NAME, cuber).getValue());
        model.addAttribute("moderator", cuber.getAuthorities().contains(roleService.getRoleByName("ROLE_MODERATOR")));
        log.debug("User with nickname " + cuber.getNickname() + " got training page");
        List<CubeResponse> cubes = cubeService.getAllCubes(cuber);
        model.addAttribute("cubes", cubes);
        if (cube == null) {
            model.addAttribute("methods", cubes.stream().flatMap(cubeResponse -> cubeResponse.getMethods().stream()).collect(Collectors.toList()));
            log.trace("A specific cube is not selected or there is no cube with this name, methods for all cubes will be shown");
        } else {
            model.addAttribute("methods", cube.getMethods());
            model.addAttribute("selectedCube", cube);
            log.trace("Selected cube: " + cube.getName());
        }
        return "training";
    }

    @GetMapping("/method/{method-name}")
    public String getTrainingPageForMethod(Model model, @PathVariable(value = "method-name", required = false) MethodResponse method) {
        if (method == null) {
            log.debug("A specific method is not selected or there is no method with this name, redirect to the main algorithms page");
            return "redirect:/AlgLearning/training";
        }
        ModelAttributesUtil.setDefaultAttributes(model);
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        cuberService.updateLastVisited(cuber);
        model.addAttribute("background", settingService.getSetting(SettingConstants.BACKGROUND_SETTING_NAME, cuber).getValue());
        model.addAttribute("moderator", cuber.getAuthorities().contains(roleService.getRoleByName("ROLE_MODERATOR")));
        SituationWithScrambleResponse situationWithScramble = methodService.getNextTrainingSituationInMethod(method.getId(), cuber);
        List<SituationResponse> situations = situationService.getSituationsByMethodIdAndSearchExpression(method.getId(), "", cuber);
        log.debug("User with nickname " + cuber.getNickname() + " got training page for " + method.getName());
        model.addAttribute("method", method);
        model.addAttribute("situations", situations);
        model.addAttribute("situation", situationWithScramble.getSituation());
        model.addAttribute("scramble", situationWithScramble.getScramble());
        model.addAttribute("hideNumberOfAlgorithmsInTraining", settingService.getSetting(SettingConstants.HIDE_NUMBER_OF_ALGORITHMS_IN_TRAINING_SETTING_NAME, cuber).getValue());
        model.addAttribute("hideSituationInTraining", settingService.getSetting(SettingConstants.HIDE_SITUATION_IN_TRAINING_SETTING_NAME, cuber).getValue());
        model.addAttribute("defaultIncludeNotLearningInTraining", settingService.getSetting(SettingConstants.DEFAULT_INCLUDE_NOT_LEARNING_IN_TRAINING_SETTING_NAME, cuber).getValue());
        model.addAttribute("defaultIncludeLearningInTraining", settingService.getSetting(SettingConstants.DEFAULT_INCLUDE_LEARNING_IN_TRAINING_SETTING_NAME, cuber).getValue());
        model.addAttribute("defaultIncludeLearnedInTraining", settingService.getSetting(SettingConstants.DEFAULT_INCLUDE_LEARNED_IN_TRAINING_SETTING_NAME, cuber).getValue());
        log.trace("Hide situation in training? " + model.getAttribute("hideSituationInTraining"));
        log.trace("Hide numbers of algorithms in training? " + model.getAttribute("hideNumberOfAlgorithmsInTraining"));
        log.trace("Include by default not learning situations? " + model.getAttribute("defaultIncludeNotLearningInTraining"));
        log.trace("Include by default learning situations? " + model.getAttribute("defaultIncludeLearningInTraining"));
        log.trace("Include by default learned situations? " + model.getAttribute("defaultIncludeLearnedInTraining"));
        log.trace("Next situation is " + situationWithScramble.getSituation().getName() + " with scramble: " + situationWithScramble.getScramble());
        return "method-training";
    }
}
