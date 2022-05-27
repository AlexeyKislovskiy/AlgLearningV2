package fertdt.alglearningv2.util.mapper.impl;

import fertdt.alglearningv2.dto.response.CubeResponse;
import fertdt.alglearningv2.model.CubeEntity;
import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.util.mapper.CubeMapper;
import fertdt.alglearningv2.util.mapper.MethodMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CubeMapperImpl implements CubeMapper {
    private final MethodMapper methodMapper;

    @Override
    public CubeResponse toResponse(CubeEntity cubeEntity, CuberEntity cuber) {
        return CubeResponse.builder()
                .id(cubeEntity.getId())
                .description(cubeEntity.getDescription())
                .image(cubeEntity.getImage())
                .name(cubeEntity.getName())
                .methods(methodMapper.toResponse(cubeEntity.getMethods(), cuber))
                .build();
    }

    @Override
    public List<CubeResponse> toResponse(List<CubeEntity> cubeEntities, CuberEntity cuber) {
        return cubeEntities.stream().map(cube -> toResponse(cube, cuber)).collect(Collectors.toList());
    }
}
