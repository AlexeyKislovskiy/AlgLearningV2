package fertdt.alglearningv2.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cuber")
public class CuberEntity extends AbstractEntity implements UserDetails {
    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "hashed_password", nullable = false)
    private String hashedPassword;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Column(name = "visited_days", nullable = false)
    private Integer visitedDays;

    @Column(name = "visited_days_row", nullable = false)
    private Integer visitedDaysRow;

    @Column(name = "last_visited", nullable = false)
    private LocalDate lastVisited;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "addCuber")
    private Set<AlgorithmEntity> addedAlgorithms;

    @ManyToMany
    @JoinTable(
            name = "cuber_using_algorithm",
            joinColumns = @JoinColumn(name = "cuber_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "algorithm_id", referencedColumnName = "id"))
    private Set<AlgorithmEntity> usingAlgorithms;

    @OneToMany(mappedBy = "cuber")
    private Set<CuberLearningSituationEntity> learningSituations;

    @ManyToMany
    @JoinTable(
            name = "cuber_learned_situation",
            joinColumns = @JoinColumn(name = "cuber_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "situation_id", referencedColumnName = "id"))
    private Set<SituationEntity> learnedSituations;

    @ManyToMany
    @JoinTable(
            name = "cuber_training_situation",
            joinColumns = @JoinColumn(name = "cuber_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "situation_id", referencedColumnName = "id"))
    private Set<SituationEntity> trainingSituations;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "cuber_role",
            joinColumns = @JoinColumn(name = "cuber_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> authorities = new HashSet<>();

    @OneToMany(mappedBy = "cuber")
    private List<StatisticsEntity> statistics;

    @Override
    public String getPassword() {
        return hashedPassword;
    }

    @Override
    public String getUsername() {
        return nickname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
