package fertdt.alglearningv2.util.mapper.impl;

import fertdt.alglearningv2.dto.request.CuberRegistrationRequest;
import fertdt.alglearningv2.dto.response.CuberResponse;
import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.util.mapper.CuberMapper;
import fertdt.alglearningv2.util.mapper.StatisticsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class CuberMapperImpl implements CuberMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final StatisticsMapper statisticsMapper;

    @Override
    public CuberResponse toResponse(CuberEntity cuberEntity) {
        if (cuberEntity == null) return null;
        else return CuberResponse.builder()
                .id(cuberEntity.getId())
                .email(cuberEntity.getEmail())
                .nickname(cuberEntity.getNickname())
                .registrationDate(cuberEntity.getRegistrationDate().toString())
                .visitedDays(cuberEntity.getVisitedDays())
                .visitedDaysRow(cuberEntity.getVisitedDaysRow())
                .numberOfLearning(cuberEntity.getLearningSituations().size())
                .numberOfLearned(cuberEntity.getLearnedSituations().size())
                .statistics(statisticsMapper.toResponse(cuberEntity.getStatistics()))
                .build();
    }

    @Override
    public CuberEntity toEntity(CuberRegistrationRequest cuberRegistrationRequest) {
        return CuberEntity.builder()
                .nickname(cuberRegistrationRequest.getNickname())
                .email(cuberRegistrationRequest.getEmail())
                .hashedPassword(passwordEncoder.encode(cuberRegistrationRequest.getPassword()))
                .registrationDate(LocalDate.now())
                .visitedDays(1)
                .visitedDaysRow(1)
                .lastVisited(LocalDate.now())
                .build();
    }
}
