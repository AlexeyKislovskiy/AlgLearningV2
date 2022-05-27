package fertdt.alglearningv2.service.impl;

import fertdt.alglearningv2.exception.SituationNotFoundException;
import fertdt.alglearningv2.model.AlgorithmEntity;
import fertdt.alglearningv2.model.SituationEntity;
import fertdt.alglearningv2.repository.SituationRepository;
import fertdt.alglearningv2.service.ScrambleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScrambleServiceImpl implements ScrambleService {
    private final SituationRepository situationRepository;

    @Override
    public String getScramble(UUID situationId) {
        SituationEntity situation = situationRepository.findById(situationId).orElseThrow(SituationNotFoundException::new);
        List<String> algorithmTexts = situation.getAlgorithms().stream().filter(AlgorithmEntity::isVerified).
                map(AlgorithmEntity::getText).collect(Collectors.toList());
        String algorithmText = algorithmTexts.get(new Random().nextInt(algorithmTexts.size()));
        String cubeName = situation.getMethod().getCube().getName();
        if (cubeName.equals("2x2") || cubeName.equals("3x3")) return getScrambleForCubicCubes(algorithmText);
        else return "";
    }

    private String getScrambleForCubicCubes(String algorithmText) {
        StringBuilder scramble = new StringBuilder();
        String[] moves = algorithmText.split(" ");
        int firstMove = 0;
        for (String move : moves) {
            if (move.startsWith("x") || move.startsWith("y") || move.startsWith("z")) firstMove++;
            else break;
        }
        for (int i = moves.length - 1; i >= firstMove; i--) {
            String move = moves[i];
            if (move.endsWith("'")) scramble.append(move, 0, move.length() - 1);
            else if (move.endsWith("2")) scramble.append(move);
            else scramble.append(move).append("'");
            if (i != 0) scramble.append(" ");
        }
        return scramble.toString();
    }
}
