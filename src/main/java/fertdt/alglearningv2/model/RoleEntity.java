package fertdt.alglearningv2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "role")
public class RoleEntity extends AbstractEntity implements GrantedAuthority {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}

