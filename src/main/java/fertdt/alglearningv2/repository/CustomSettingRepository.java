package fertdt.alglearningv2.repository;

import fertdt.alglearningv2.model.CuberEntity;
import fertdt.alglearningv2.model.CustomSettingEntity;
import fertdt.alglearningv2.model.DefaultSettingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomSettingRepository extends JpaRepository<CustomSettingEntity, UUID> {
    Optional<CustomSettingEntity> findBySettingAndCuber(DefaultSettingEntity defaultSetting, CuberEntity cuber);
}
