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
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "file_info")
public class FileInfoEntity extends AbstractEntity {
    @Column(name = "size", nullable = false)
    private Long size;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "original_file_name", nullable = false)
    private String originalFileName;

    @Column(name = "storage_file_name", nullable = false)
    private String storageFileName;
}
