package fertdt.alglearningv2.controller;

import fertdt.alglearningv2.constants.SettingConstants;
import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.service.CuberService;
import fertdt.alglearningv2.service.RoleService;
import fertdt.alglearningv2.service.SettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/AlgLearning/account")
public class AccountController {
    private final CuberService cuberService;
    private final SettingService settingService;
    private final RoleService roleService;

    @GetMapping
    public String getBasicAccountPage(Model model) {
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        cuberService.updateLastVisited(cuber);
        model.addAttribute("background", settingService.getSetting(SettingConstants.BACKGROUND_SETTING_NAME, cuber).getValue());
        model.addAttribute("moderator", cuber.getAuthorities().contains(roleService.getRoleByName("ROLE_MODERATOR")));
        log.debug("User with nickname " + cuber.getNickname() + " got account page");
        model.addAttribute("cuber", cuberService.getCuberResponse(cuber));
        return "account";
    }
}
