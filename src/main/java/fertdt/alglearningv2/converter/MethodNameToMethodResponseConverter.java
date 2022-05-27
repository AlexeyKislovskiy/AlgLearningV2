package fertdt.alglearningv2.converter;


import fertdt.alglearningv2.dto.response.MethodResponse;
import fertdt.alglearningv2.exception.MethodNotFoundException;
import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.service.CuberService;
import fertdt.alglearningv2.service.MethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MethodNameToMethodResponseConverter implements Converter<String, MethodResponse> {
    @Autowired
    private MethodService methodService;
    @Autowired
    private CuberService cuberService;

    @Override
    public MethodResponse convert(String methodName) {
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        try {
            return methodService.getMethodByName(methodName, cuber);
        } catch (MethodNotFoundException e) {
            return null;
        }
    }
}
