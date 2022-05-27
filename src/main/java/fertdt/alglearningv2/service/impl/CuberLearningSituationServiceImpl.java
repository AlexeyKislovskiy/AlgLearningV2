package fertdt.alglearningv2.service.impl;

import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.model.CuberLearningSituationEntity;
import fertdt.alglearningv2.repository.CuberLearningSituationRepository;
import fertdt.alglearningv2.service.CuberLearningSituationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CuberLearningSituationServiceImpl implements CuberLearningSituationService {
    private final CuberLearningSituationRepository cuberLearningSituationRepository;

    @Override
    public void save(CuberLearningSituationEntity cuberLearningSituationEntity) {
        cuberLearningSituationRepository.save(cuberLearningSituationEntity);
    }

    @Override
    public void delete(CuberLearningSituationEntity cuberLearningSituationEntity) {
        cuberLearningSituationRepository.delete(cuberLearningSituationEntity);
    }

    @Override
    public void updateAllStatus(CuberEntity cuber) {
        cuberLearningSituationRepository.updateAllStatus(cuber);
    }
}
