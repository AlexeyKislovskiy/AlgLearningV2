package fertdt.alglearningv2.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PasswordUtil {
    public static boolean passwordCorrect(String password) {
        boolean smallLetter = false, capitalLetter = false, digit = false;
        for (int i = 0; i < password.length(); i++) {
            int current = password.charAt(i);
            if (current >= 'a' && current <= 'z') smallLetter = true;
            else if (current >= 'A' && current <= 'Z') capitalLetter = true;
            else if (current >= '0' && current <= '9') digit = true;
        }
        return password.length() >= 8 && smallLetter && capitalLetter && digit;
    }
}
