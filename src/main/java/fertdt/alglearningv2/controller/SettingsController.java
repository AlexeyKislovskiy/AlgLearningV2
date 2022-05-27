package fertdt.alglearningv2.controller;

import fertdt.alglearningv2.constants.SettingConstants;
import fertdt.alglearningv2.dto.request.SettingRequest;
import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.service.CuberService;
import fertdt.alglearningv2.service.RoleService;
import fertdt.alglearningv2.service.SettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/AlgLearning/settings")
public class SettingsController {
    private final CuberService cuberService;
    private final SettingService settingService;
    private final RoleService roleService;

    @GetMapping
    public String getBasicSettingsPage(Model model) {
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        cuberService.updateLastVisited(cuber);
        model.addAttribute("moderator", cuber.getAuthorities().contains(roleService.getRoleByName("ROLE_MODERATOR")));
        log.debug("User with nickname " + cuber.getNickname() + " got settings page");
        model.addAttribute("hideSituationInLearning", settingService.getSetting(SettingConstants.HIDE_SITUATION_IN_LEARNING_SETTING_NAME, cuber).getValue());
        model.addAttribute("hideNumberOfAlgorithmsInLearning", settingService.getSetting(SettingConstants.HIDE_NUMBER_OF_ALGORITHMS_IN_LEARNING_SETTING_NAME, cuber).getValue());
        model.addAttribute("hardMultiplier", settingService.getSetting(SettingConstants.HARD_MULTIPLIER_SETTING_NAME, cuber).getValue());
        model.addAttribute("mediumMultiplier", settingService.getSetting(SettingConstants.MEDIUM_MULTIPLIER_SETTING_NAME, cuber).getValue());
        model.addAttribute("easyMultiplier", settingService.getSetting(SettingConstants.EASY_MULTIPLIER_SETTING_NAME, cuber).getValue());
        model.addAttribute("againMultiplier", settingService.getSetting(SettingConstants.AGAIN_MULTIPLIER_SETTING_NAME, cuber).getValue());
        model.addAttribute("resetWhenForgot", settingService.getSetting(SettingConstants.RESET_WHEN_FORGOT_SETTING_NAME, cuber).getValue());
        model.addAttribute("firstInterval", settingService.getSetting(SettingConstants.FIRST_INTERVAL_SETTING_NAME, cuber).getValue());
        model.addAttribute("hideSituationInTraining", settingService.getSetting(SettingConstants.HIDE_SITUATION_IN_TRAINING_SETTING_NAME, cuber).getValue());
        model.addAttribute("hideNumberOfAlgorithmsInTraining", settingService.getSetting(SettingConstants.HIDE_NUMBER_OF_ALGORITHMS_IN_TRAINING_SETTING_NAME, cuber).getValue());
        model.addAttribute("defaultIncludeNotLearningInTraining", settingService.getSetting(SettingConstants.DEFAULT_INCLUDE_NOT_LEARNING_IN_TRAINING_SETTING_NAME, cuber).getValue());
        model.addAttribute("defaultIncludeLearningInTraining", settingService.getSetting(SettingConstants.DEFAULT_INCLUDE_LEARNING_IN_TRAINING_SETTING_NAME, cuber).getValue());
        model.addAttribute("defaultIncludeLearnedInTraining", settingService.getSetting(SettingConstants.DEFAULT_INCLUDE_LEARNED_IN_TRAINING_SETTING_NAME, cuber).getValue());
        model.addAttribute("defaultSorting", settingService.getSetting(SettingConstants.DEFAULT_SORTING_SETTING_NAME, cuber).getValue());
        model.addAttribute("showUsingAlgorithmsFirst", settingService.getSetting(SettingConstants.SHOW_USING_ALGORITHMS_FIRST_SETTING_NAME, cuber).getValue());
        model.addAttribute("interceptionAsMove", settingService.getSetting(SettingConstants.INTERCEPTION_AS_MOVE_SETTING_NAME, cuber).getValue());
        model.addAttribute("doubleMoveAs2Moves", settingService.getSetting(SettingConstants.DOUBLE_MOVE_AS_2_MOVES_SETTING_NAME, cuber).getValue());
        model.addAttribute("checkAll", settingService.getSetting(SettingConstants.CHECK_ALL_SETTING_NAME, cuber).getValue());
        model.addAttribute("plusAll", settingService.getSetting(SettingConstants.PLUS_ALL_SETTING_NAME, cuber).getValue());
        model.addAttribute("deleteAll", settingService.getSetting(SettingConstants.DELETE_ALL_SETTING_NAME, cuber).getValue());
        model.addAttribute("showUnverifiedAlgorithms", settingService.getSetting(SettingConstants.SHOW_UNVERIFIED_ALGORITHMS_SETTING_NAME, cuber).getValue());
        model.addAttribute("useAddedAlgorithm", settingService.getSetting(SettingConstants.USE_ADDED_ALGORITHM_SETTING_NAME, cuber).getValue());
        model.addAttribute("background", settingService.getSetting(SettingConstants.BACKGROUND_SETTING_NAME, cuber).getValue());
        return "settings";
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping()
    public void changeSetting(@RequestBody SettingRequest settingRequest) {
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        log.debug("User with nickname " + cuber.getNickname() + " change setting " + settingRequest.getName() +
                " to value " + settingRequest.getValue());
        settingService.changeSetting(settingRequest.getName(), settingRequest.getValue(), cuber);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{block}")
    public void resetSettings(@PathVariable String block) {
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        log.debug("User with nickname " + cuber.getNickname() + " reset settings in " + block);
        settingService.resetSettings(block, cuber);
    }
}
