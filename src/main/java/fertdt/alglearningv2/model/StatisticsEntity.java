package fertdt.alglearningv2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "statistics")
public class StatisticsEntity extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "cuber_id", nullable = false)
    CuberEntity cuber;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "number_of_forgot", nullable = false)
    private Integer numberOfForgot;

    @Column(name = "number_of_repeat", nullable = false)
    private Integer numberOfRepeat;

    @Column(name = "number_of_new", nullable = false)
    private Integer numberOfNew;

    @Column(name = "number_of_trained", nullable = false)
    private Integer numberOfTrained;
}
