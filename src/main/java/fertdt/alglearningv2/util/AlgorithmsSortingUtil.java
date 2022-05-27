package fertdt.alglearningv2.util;

import fertdt.alglearningv2.dto.enums.AlgorithmUsingStatus;
import fertdt.alglearningv2.dto.response.AlgorithmResponse;
import fertdt.alglearningv2.model.SettingEntity;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@UtilityClass
public class AlgorithmsSortingUtil {
    public static void sortByPopularity(List<AlgorithmResponse> algorithmResponses) {
        algorithmResponses.sort((a, b) -> b.getNumberOfUses().compareTo(a.getNumberOfUses()));
    }

    public static void sortByLength(List<AlgorithmResponse> algorithmResponses, SettingEntity interceptionAsMoveSetting, SettingEntity doubleMoveAs2MovesSetting) {
        algorithmResponses.sort(Comparator.comparing(a -> AlgorithmUtil.algorithmLength(a.getText(), interceptionAsMoveSetting, doubleMoveAs2MovesSetting)));
    }

    public static void usingFirst(List<AlgorithmResponse> algorithmResponses) {
        List<AlgorithmResponse> resortedList = new ArrayList<>();
        for (AlgorithmResponse algorithmResponse : algorithmResponses) {
            if (algorithmResponse.getUsingStatus().equals(AlgorithmUsingStatus.USING))
                resortedList.add(algorithmResponse);
        }
        for (AlgorithmResponse algorithmResponse : algorithmResponses) {
            if (algorithmResponse.getUsingStatus().equals(AlgorithmUsingStatus.NOT_USING))
                resortedList.add(algorithmResponse);
        }
        algorithmResponses.removeAll(resortedList);
        algorithmResponses.addAll(resortedList);
    }
}
