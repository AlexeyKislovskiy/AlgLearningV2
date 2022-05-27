package fertdt.alglearningv2.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cuber_learning_situation")
public class CuberLearningSituationEntity extends AbstractEntity{
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "cuber_id", referencedColumnName = "id", nullable = false)
    private CuberEntity cuber;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "situation_id", referencedColumnName = "id", nullable = false)
    private SituationEntity situation;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CuberLearningSituationStatus status;

    @Column(name = "last_interval", nullable = false)
    private Double lastInterval;

    @Column(name = "next_repeat", nullable = false)
    private LocalDate nextRepeat;

}
