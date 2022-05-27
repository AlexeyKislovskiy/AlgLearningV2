package fertdt.alglearningv2.service.impl;

import fertdt.alglearningv2.exception.RoleNotFoundException;
import fertdt.alglearningv2.model.RoleEntity;
import fertdt.alglearningv2.repository.RoleRepository;
import fertdt.alglearningv2.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public RoleEntity getRoleByName(String name) {
        return roleRepository.findByName(name).orElseThrow(RoleNotFoundException::new);
    }
}
