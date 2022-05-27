package fertdt.alglearningv2.repository;

import fertdt.alglearningv2.model.DefaultSettingEntity;
import fertdt.alglearningv2.model.SettingBlock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DefaultSettingRepository extends JpaRepository<DefaultSettingEntity, UUID> {
    Optional<DefaultSettingEntity> findByName(String name);

    List<DefaultSettingEntity> findAllByBlock(SettingBlock block);
}
