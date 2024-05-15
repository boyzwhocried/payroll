package com.lawencon.payroll.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lawencon.payroll.constant.Roles;
import com.lawencon.payroll.dto.role.RoleResDto;
import com.lawencon.payroll.model.Role;
import com.lawencon.payroll.repository.RoleRepository;
import com.lawencon.payroll.service.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getIdByRole(String code) {
        return roleRepository.findByRoleCode(code);
    }

    @Override
    public List<RoleResDto> getRolesExcept() {
        final List<RoleResDto> rolesRes = new ArrayList<>();
        
        final List<Role> roles = roleRepository.findByRoleCodeNot(Roles.RL001.name());

        final int size = roles.size();
        for(int i=0;i<size;i++) {
            final Role role = roles.get(i);

            final RoleResDto roleRes = new RoleResDto();

            roleRes.setId(role.getId());
            roleRes.setCode(role.getRoleCode());
            roleRes.setName(role.getRoleName());

            rolesRes.add(roleRes);
        }

        return rolesRes;
    }

    @Override
    public Role getById(String id) {
        final var role = roleRepository.findById(id);
        return role.get();
    }

}
