package fertdt.alglearningv2.util.mapper;

import fertdt.alglearningv2.dto.response.AlgorithmApiResponse;
import fertdt.alglearningv2.dto.response.AlgorithmResponse;
import fertdt.alglearningv2.model.AlgorithmEntity;
import fertdt.alglearningv2.model.CuberEntity;

import java.util.List;


public interface AlgorithmMapper {
    AlgorithmResponse toResponse(AlgorithmEntity algorithmEntity, CuberEntity cuber);

    AlgorithmApiResponse toResponse(AlgorithmEntity algorithmEntity);

    List<AlgorithmResponse> toResponse(List<AlgorithmEntity> algorithmEntities, CuberEntity cuber);

    List<AlgorithmApiResponse> toResponse(List<AlgorithmEntity> algorithmEntities);
}
