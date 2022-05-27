package fertdt.alglearningv2.util.mapper;

import fertdt.alglearningv2.dto.response.MethodResponse;
import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.model.MethodEntity;

import java.util.List;

public interface MethodMapper {
    MethodResponse toResponse(MethodEntity methodEntity, CuberEntity cuber);

    List<MethodResponse> toResponse(List<MethodEntity> methodEntities, CuberEntity cuber);
}
