package fertdt.alglearningv2.service;

import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.model.SettingEntity;

public interface SettingService {
    SettingEntity getSetting(String settingName, CuberEntity cuber);

    void changeSetting(String name, String value, CuberEntity cuber);

    void resetSettings(String block, CuberEntity cuber);
}
