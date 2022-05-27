package fertdt.alglearningv2.service.impl;

import fertdt.alglearningv2.constants.SettingConstants;
import fertdt.alglearningv2.dto.request.AlgorithmRequest;
import fertdt.alglearningv2.dto.response.AlgorithmApiResponse;
import fertdt.alglearningv2.exception.AlgorithmNotFoundException;
import fertdt.alglearningv2.exception.SameAlgorithmsAlreadyExistException;
import fertdt.alglearningv2.exception.rest.AlgorithmNotFoundApiException;
import fertdt.alglearningv2.exception.rest.SameAlgorithmAlreadyExistApiException;
import fertdt.alglearningv2.model.AlgorithmEntity;
import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.model.SettingEntity;
import fertdt.alglearningv2.repository.AlgorithmRepository;
import fertdt.alglearningv2.service.AlgorithmService;
import fertdt.alglearningv2.service.CuberService;
import fertdt.alglearningv2.service.SettingService;
import fertdt.alglearningv2.service.SituationService;
import fertdt.alglearningv2.util.mapper.AlgorithmMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlgorithmServiceImpl implements AlgorithmService {
    private final AlgorithmRepository algorithmRepository;
    private final AlgorithmMapper algorithmMapper;
    private final CuberService cuberService;
    private final SituationService situationService;
    private final SettingService settingService;

    @Override
    public UUID getMethodIdByAlgorithmId(UUID algorithmId) {
        AlgorithmEntity algorithm = algorithmRepository.findById(algorithmId).orElseThrow(AlgorithmNotFoundException::new);
        return algorithm.getSituation().getMethod().getId();
    }

    @Override
    public void use(UUID algorithmId, CuberEntity cuber) {
        AlgorithmEntity algorithm = algorithmRepository.findById(algorithmId).orElseThrow(AlgorithmNotFoundException::new);
        if (cuber.getUsingAlgorithms().contains(algorithm)) cuber.getUsingAlgorithms().remove(algorithm);
        else cuber.getUsingAlgorithms().add(algorithm);
        cuberService.save(cuber);
    }

    @Override
    public UUID addAlgorithm(UUID situationId, String text, CuberEntity cuber) {
        if (algorithmRepository.findByTextAndSituation_Id(text, situationId).isPresent())
            throw new SameAlgorithmsAlreadyExistException();
        AlgorithmEntity algorithm = AlgorithmEntity.builder()
                .text(text)
                .addCuber(cuber)
                .verified(false)
                .situation(situationService.getSituationEntityById(situationId))
                .build();
        algorithm = algorithmRepository.save(algorithm);
        SettingEntity useAddedAlgorithmSetting = settingService.getSetting(SettingConstants.USE_ADDED_ALGORITHM_SETTING_NAME, cuber);
        if (useAddedAlgorithmSetting.getValue().equals(SettingConstants.USE_ADDED_ALGORITHM_SETTING_VALUE_TRUE)) {
            cuber.getUsingAlgorithms().add(algorithm);
            cuberService.save(cuber);
        }
        return algorithm.getId();
    }

    @Override
    public List<AlgorithmApiResponse> getAllUnverifiedAlgorithms() {
        return algorithmMapper.toResponse(algorithmRepository.findAllByVerified(false));
    }

    @Override
    public void verify(UUID algorithmId) {
        AlgorithmEntity algorithm = algorithmRepository.findById(algorithmId).orElseThrow(AlgorithmNotFoundException::new);
        algorithm.setVerified(true);
        algorithmRepository.save(algorithm);
    }

    @Override
    public void notVerify(UUID algorithmId) {
        AlgorithmEntity algorithm = algorithmRepository.findById(algorithmId).orElseThrow(AlgorithmNotFoundException::new);
        algorithmRepository.delete(algorithm);
    }

    @Override
    public AlgorithmApiResponse getAlgorithmById(UUID algorithmId) {
        return algorithmMapper.toResponse(algorithmRepository.findById(algorithmId).orElseThrow(AlgorithmNotFoundApiException::new));
    }

    @Override
    public UUID addAlgorithm(AlgorithmRequest algorithmRequest, CuberEntity cuber) {
        if (algorithmRepository.findByTextAndSituation_Id(algorithmRequest.getText(), algorithmRequest.getSituationId()).isPresent())
            throw new SameAlgorithmAlreadyExistApiException();
        AlgorithmEntity algorithm = AlgorithmEntity.builder()
                .text(algorithmRequest.getText())
                .addCuber(cuber)
                .verified(true)
                .situation(situationService.getSituationEntityByIdForApi(algorithmRequest.getSituationId()))
                .build();
        return algorithmRepository.save(algorithm).getId();
    }

    @Override
    public UUID deleteAlgorithm(UUID algorithmId) {
        AlgorithmEntity algorithm = algorithmRepository.findById(algorithmId).orElseThrow(AlgorithmNotFoundApiException::new);
        algorithmRepository.delete(algorithm);
        return algorithmId;
    }
}
