package fertdt.alglearningv2.util.mapper;

import fertdt.alglearningv2.dto.response.CubeResponse;
import fertdt.alglearningv2.model.CubeEntity;
import fertdt.alglearningv2.model.CuberEntity;

import java.util.List;


public interface CubeMapper {
    CubeResponse toResponse(CubeEntity cubeEntity, CuberEntity cuber);

    List<CubeResponse> toResponse(List<CubeEntity> cubeEntities, CuberEntity cuber);
}
