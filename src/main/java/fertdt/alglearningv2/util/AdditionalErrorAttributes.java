package fertdt.alglearningv2.util;

import fertdt.alglearningv2.constants.SettingConstants;
import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.service.CuberService;
import fertdt.alglearningv2.service.RoleService;
import fertdt.alglearningv2.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class AdditionalErrorAttributes extends DefaultErrorAttributes {

    private final CuberService cuberService;
    private final SettingService settingService;
    private final RoleService roleService;

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        errorAttributes.put("background", settingService.getSetting(SettingConstants.BACKGROUND_SETTING_NAME, cuber).getValue());
        errorAttributes.put("moderator", cuber.getAuthorities().contains(roleService.getRoleByName("ROLE_MODERATOR")));
        return errorAttributes;
    }

}
