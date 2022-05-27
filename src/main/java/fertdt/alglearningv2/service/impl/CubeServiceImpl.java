package fertdt.alglearningv2.service.impl;

import fertdt.alglearningv2.dto.response.CubeResponse;
import fertdt.alglearningv2.exception.CubeNotFoundException;
import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.repository.CubeRepository;
import fertdt.alglearningv2.service.CubeService;
import fertdt.alglearningv2.util.mapper.CubeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CubeServiceImpl implements CubeService {
    private final CubeRepository cubeRepository;
    private final CubeMapper cubeMapper;

    @Override
    public List<CubeResponse> getAllCubes(CuberEntity cuber) {
        return cubeMapper.toResponse(cubeRepository.findAll(), cuber);
    }

    @Override
    public CubeResponse getCubeByName(String name, CuberEntity cuber) {
        return cubeMapper.toResponse(cubeRepository.findByName(name).orElseThrow(CubeNotFoundException::new), cuber);
    }
}
