package fertdt.alglearningv2.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "algorithm")
public class AlgorithmEntity extends AbstractEntity {
    @Column(name = "text", nullable = false, columnDefinition = "TEXT")
    private String text;

    @ManyToOne
    @JoinColumn(name = "situation_id", nullable = false)
    private SituationEntity situation;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "usingAlgorithms")
    private Set<CuberEntity> uses;

    @Column(name = "verified", nullable = false)
    private boolean verified;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "add_cuber_id")
    private CuberEntity addCuber;
}
