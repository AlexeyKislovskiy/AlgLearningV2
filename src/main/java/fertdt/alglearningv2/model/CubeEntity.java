package fertdt.alglearningv2.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cube")
public class CubeEntity extends AbstractEntity {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "description", nullable = false)
    private String description;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "cube")
    private List<MethodEntity> methods;
}
