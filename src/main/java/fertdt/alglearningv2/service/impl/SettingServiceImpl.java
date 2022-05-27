package fertdt.alglearningv2.service.impl;

import fertdt.alglearningv2.constants.SettingConstants;
import fertdt.alglearningv2.exception.SettingNotFoundException;
import fertdt.alglearningv2.model.*;
import fertdt.alglearningv2.repository.CustomSettingRepository;
import fertdt.alglearningv2.repository.DefaultSettingRepository;
import fertdt.alglearningv2.service.FileService;
import fertdt.alglearningv2.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SettingServiceImpl implements SettingService {
    private final DefaultSettingRepository defaultSettingRepository;
    private final CustomSettingRepository customSettingRepository;
    private final FileService fileService;

    @Override
    public SettingEntity getSetting(String settingName, CuberEntity cuber) {
        DefaultSettingEntity defaultSetting = defaultSettingRepository.findByName(settingName).orElseThrow(SettingNotFoundException::new);
        CustomSettingEntity customSetting = customSettingRepository.findBySettingAndCuber(defaultSetting, cuber).orElse(null);
        return SettingEntity.builder()
                .name(defaultSetting.getName())
                .value(customSetting == null ? defaultSetting.getDefaultValue() : customSetting.getCustomValue())
                .block(defaultSetting.getBlock())
                .build();
    }

    @Override
    public void changeSetting(String name, String value, CuberEntity cuber) {
        DefaultSettingEntity defaultSetting = defaultSettingRepository.findByName(name).orElseThrow(SettingNotFoundException::new);
        CustomSettingEntity customSetting = customSettingRepository.findBySettingAndCuber(defaultSetting, cuber).orElse(null);
        if (value.equals(defaultSetting.getDefaultValue())) {
            if (customSetting != null) customSettingRepository.delete(customSetting);
        } else {
            if (customSetting != null) {
                customSetting.setCustomValue(value);
            } else {
                customSetting = CustomSettingEntity.builder()
                        .setting(defaultSetting)
                        .customValue(value)
                        .cuber(cuber)
                        .build();
            }
            customSettingRepository.save(customSetting);
        }
        if (name.equals(SettingConstants.BACKGROUND_SETTING_NAME) && customSetting != null) {
//            fileService.delete(customSetting.getCustomValue());
        }
    }

    @Override
    public void resetSettings(String block, CuberEntity cuber) {
        List<DefaultSettingEntity> settings;
        if (block.equals("ALL")) settings = defaultSettingRepository.findAll();
        else settings = defaultSettingRepository.findAllByBlock(SettingBlock.valueOf(block));
        for (DefaultSettingEntity setting : settings) {
            changeSetting(setting.getName(), setting.getDefaultValue(), cuber);
        }
    }
}
