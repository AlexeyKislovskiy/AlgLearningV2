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
@Table(name = "custom_setting")
public class CustomSettingEntity extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "setting_id", nullable = false)
    private DefaultSettingEntity setting;

    @ManyToOne
    @JoinColumn(name = "cuber_id", nullable = false)
    private CuberEntity cuber;

    @Column(name = "custom_value", nullable = false, columnDefinition = "TEXT")
    private String customValue;
}
