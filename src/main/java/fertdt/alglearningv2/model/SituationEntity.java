package fertdt.alglearningv2.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "situation")
public class SituationEntity extends AbstractEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "method_id", nullable = false)
    private MethodEntity method;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "situation")
    private List<AlgorithmEntity> algorithms;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "situation")
    private Set<CuberLearningSituationEntity> learning;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "learnedSituations")
    private Set<CuberEntity> learned;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "trainingSituations")
    private Set<CuberEntity> training;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne
    @JoinColumn(name = "mirror_id")
    private SituationEntity mirror;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne
    @JoinColumn(name = "reverse_id")
    private SituationEntity reverse;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne
    @JoinColumn(name = "mirror_reverse_id")
    private SituationEntity mirrorReverse;

    @Column(name = "image", nullable = false)
    private String image;
}
