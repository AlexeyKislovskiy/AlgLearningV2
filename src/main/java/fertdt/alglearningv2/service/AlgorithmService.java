package fertdt.alglearningv2.service;

import fertdt.alglearningv2.dto.request.AlgorithmRequest;
import fertdt.alglearningv2.dto.response.AlgorithmApiResponse;
import fertdt.alglearningv2.model.CuberEntity;

import java.util.List;
import java.util.UUID;

public interface AlgorithmService {
    UUID getMethodIdByAlgorithmId(UUID algorithmId);

    void use(UUID algorithmId, CuberEntity cuber);

    UUID addAlgorithm(UUID situationId, String text, CuberEntity cuber);

    List<AlgorithmApiResponse> getAllUnverifiedAlgorithms();

    void verify(UUID algorithmId);

    void notVerify(UUID algorithmId);

    AlgorithmApiResponse getAlgorithmById(UUID algorithmId);

    UUID addAlgorithm(AlgorithmRequest algorithmRequest, CuberEntity cuber);

    UUID deleteAlgorithm(UUID algorithmId);
}
