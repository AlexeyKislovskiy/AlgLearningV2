package fertdt.alglearningv2.service;

import fertdt.alglearningv2.dto.response.CubeResponse;
import fertdt.alglearningv2.model.CuberEntity;

import java.util.List;

public interface CubeService {
    List<CubeResponse> getAllCubes(CuberEntity cuber);

    CubeResponse getCubeByName(String name, CuberEntity cuber);
}
