package fertdt.alglearningv2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SettingEntity {
    private String name;

    private String value;

    private SettingBlock block;
}
