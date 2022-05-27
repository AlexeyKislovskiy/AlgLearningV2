package fertdt.alglearningv2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "default_setting")
public class DefaultSettingEntity extends AbstractEntity {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "default_value", nullable = false, columnDefinition = "TEXT")
    private String defaultValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "block", nullable = false)
    private SettingBlock block;
}
