package fertdt.alglearningv2.controller;

import fertdt.alglearningv2.constants.SettingConstants;
import fertdt.alglearningv2.dto.response.CubeResponse;
import fertdt.alglearningv2.dto.response.MethodResponse;
import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.service.CubeService;
import fertdt.alglearningv2.service.CuberService;
import fertdt.alglearningv2.service.RoleService;
import fertdt.alglearningv2.service.SettingService;
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
@RequestMapping("/AlgLearning/algorithms")
public class AlgorithmsController {
    private final CuberService cuberService;
    private final CubeService cubeService;
    private final SettingService settingService;
    private final RoleService roleService;

    @GetMapping
    public String getBasicAlgorithmsPage(Model model, @RequestParam(required = false, name = "cube") CubeResponse cube) {
        ModelAttributesUtil.setDefaultAttributes(model);
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        cuberService.updateLastVisited(cuber);
        model.addAttribute("background", settingService.getSetting(SettingConstants.BACKGROUND_SETTING_NAME, cuber).getValue());
        model.addAttribute("moderator", cuber.getAuthorities().contains(roleService.getRoleByName("ROLE_MODERATOR")));
        log.debug("User with nickname " + cuber.getNickname() + " got algorithms page");
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
        return "algorithms";
    }

    @GetMapping("/method/{method-name}")
    public String getAlgorithmsPageForMethod(Model model, @PathVariable(value = "method-name", required = false) MethodResponse method) {
        if (method == null) {
            log.debug("A specific method is not selected or there is no method with this name, redirect to the main algorithms page");
            return "redirect:/AlgLearning/algorithms";
        }
        ModelAttributesUtil.setDefaultAttributes(model);
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        cuberService.updateLastVisited(cuber);
        model.addAttribute("background", settingService.getSetting(SettingConstants.BACKGROUND_SETTING_NAME, cuber).getValue());
        model.addAttribute("moderator", cuber.getAuthorities().contains(roleService.getRoleByName("ROLE_MODERATOR")));
        log.debug("User with nickname " + cuber.getNickname() + " got algorithms page for " + method.getName());
        model.addAttribute("method", method);
        model.addAttribute("sorting", settingService.getSetting(SettingConstants.DEFAULT_SORTING_SETTING_NAME, cuber).getValue());
        log.trace("Default selected sorting: by " + model.getAttribute("sorting"));
        return "algorithms-method";
    }

}
