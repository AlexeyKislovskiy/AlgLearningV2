package fertdt.alglearningv2.util.mapper;

import fertdt.alglearningv2.dto.request.CuberRegistrationRequest;
import fertdt.alglearningv2.dto.response.CuberResponse;
import fertdt.alglearningv2.model.CuberEntity;


public interface CuberMapper {
    CuberResponse toResponse(CuberEntity cuberEntity);

    CuberEntity toEntity(CuberRegistrationRequest cuberRegistrationRequest);
}
