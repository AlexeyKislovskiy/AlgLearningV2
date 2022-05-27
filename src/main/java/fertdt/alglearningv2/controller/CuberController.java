package fertdt.alglearningv2.controller;

import fertdt.alglearningv2.constants.SettingConstants;
import fertdt.alglearningv2.dto.request.CuberLoginRequest;
import fertdt.alglearningv2.dto.request.CuberRegistrationRequest;
import fertdt.alglearningv2.dto.response.CubeResponse;
import fertdt.alglearningv2.dto.response.ExceptionResponse;
import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.service.CubeService;
import fertdt.alglearningv2.service.CuberService;
import fertdt.alglearningv2.service.SettingService;
import fertdt.alglearningv2.util.ModelAttributesUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/AlgLearning")
public class CuberController {
    private final CuberService cuberService;
    private final CubeService cubeService;
    private final SettingService settingService;

    @GetMapping("/login")
    public String getBasicAlgorithmsPage(Model model) {
        ModelAttributesUtil.setDefaultAttributes(model);
        CuberEntity cuber = cuberService.getUnknownUser();
        model.addAttribute("background", settingService.getSetting(SettingConstants.BACKGROUND_SETTING_NAME, cuber).getValue());
        log.debug("Unauthorized user got login page");
        List<CubeResponse> cubes = cubeService.getAllCubes(cuber);
        model.addAttribute("cubes", cubes);
        model.addAttribute("methods", cubes.stream().flatMap(cubeResponse -> cubeResponse.getMethods().stream()).collect(Collectors.toList()));
        return "login";
    }

    @PostMapping("/login/attempt")
    @ResponseBody
    public List<ExceptionResponse> loginAttempt(CuberLoginRequest cuberLoginRequest) {
        log.debug("Unauthorized user try to login");
        List<ExceptionResponse> exceptions = cuberService.loginAttempt(cuberLoginRequest);
        if (exceptions.isEmpty())
            log.info("User with nickname " + cuberLoginRequest.getNickname() + " successfully login");
        else log.debug("Unsuccessfully login attempt, got " + exceptions.size() + " exceptions");
        return exceptions;
    }

    @PostMapping("/register")
    @ResponseBody
    public List<ExceptionResponse> register(CuberRegistrationRequest cuberRegistrationRequest) {
        log.debug("Unauthorized user try to register new account");
        List<ExceptionResponse> exceptions = cuberService.register(cuberRegistrationRequest);
        if (exceptions.isEmpty())
            log.info("User with nickname " + cuberRegistrationRequest.getNickname() + " successfully registered new account");
        else log.debug("Unsuccessfully register attempt, got " + exceptions.size() + " exceptions");
        return exceptions;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/delete")
    public void delete() {
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        cuberService.delete(cuber);
        log.info("User with nickname " + cuber.getNickname() + " successfully deleted account");
    }
}
