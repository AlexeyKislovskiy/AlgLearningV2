package fertdt.alglearningv2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "wca_competition")
public class WcaCompetitionEntity extends AbstractEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "link", nullable = false)
    private String link;

    @Column(name = "coordinates", nullable = false)
    private String coordinates;
}
