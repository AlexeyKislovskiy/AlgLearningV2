package fertdt.alglearningv2.repository;

import fertdt.alglearningv2.model.FileInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FileInfoRepository extends JpaRepository<FileInfoEntity, UUID> {
    Optional<FileInfoEntity> findByStorageFileName(String storageFileName);
}
