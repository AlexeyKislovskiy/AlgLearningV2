package fertdt.alglearningv2.util;

import fertdt.alglearningv2.constants.SettingConstants;
import fertdt.alglearningv2.dto.enums.AlgorithmUsingStatus;
import fertdt.alglearningv2.model.AlgorithmEntity;
import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.model.SettingEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AlgorithmUtil {
    public static AlgorithmUsingStatus getAlgorithmUsingStatus(AlgorithmEntity algorithm, CuberEntity cuber) {
        if (cuber.getUsingAlgorithms().contains(algorithm)) return AlgorithmUsingStatus.USING;
        else return AlgorithmUsingStatus.NOT_USING;
    }

    public static int numberOfUses(AlgorithmEntity algorithm) {
        return algorithm == null || algorithm.getUses() == null ? 0 : algorithm.getUses().size();
    }

    public static Integer algorithmLength(String algorithmText, SettingEntity interceptionAsMoveSetting, SettingEntity doubleMoveAs2MovesSetting) {
        String[] moves = algorithmText.split(" ");
        int ans = 0;
        boolean interceptionFlag = interceptionAsMoveSetting.getValue().equals(SettingConstants.INTERCEPTION_AS_MOVE_SETTING_VALUE_TRUE);
        boolean doubleMoveFlag = doubleMoveAs2MovesSetting.getValue().equals(SettingConstants.DOUBLE_MOVE_AS_2_MOVES_SETTING_VALUE_TRUE);
        for (String move : moves) {
            ans += moveLength(interceptionFlag, doubleMoveFlag, move);
        }
        return ans;
    }

    private static Integer moveLength(boolean interceptionFlag, boolean doubleMoveFlag, String move) {
        if (move.charAt(0) == 'x' || move.charAt(0) == 'y' || move.charAt(0) == 'z') {
            if (!interceptionFlag) return 0;
        }
        if (doubleMoveFlag && move.charAt(move.length() - 1) == '2') return 2;
        else return 1;
    }
}
