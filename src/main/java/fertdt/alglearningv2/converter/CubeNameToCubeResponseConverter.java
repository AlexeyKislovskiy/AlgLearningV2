package fertdt.alglearningv2.converter;

import fertdt.alglearningv2.dto.response.CubeResponse;
import fertdt.alglearningv2.exception.CubeNotFoundException;
import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.repository.CuberRepository;
import fertdt.alglearningv2.service.CubeService;
import fertdt.alglearningv2.service.CuberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CubeNameToCubeResponseConverter implements Converter<String, CubeResponse> {
    @Autowired
    private CubeService cubeService;
    @Autowired
    private CuberService cuberService;

    @Override
    public CubeResponse convert(String cubeName) {
        CuberEntity cuber = cuberService.getUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName());
        if (cubeName.equals("all")) return null;
        try {
            return cubeService.getCubeByName(cubeName, cuber);
        } catch (CubeNotFoundException e) {
            return null;
        }
    }
}
