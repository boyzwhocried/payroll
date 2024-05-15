package com.lawencon.payroll.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.payroll.model.Role;

@Repository
public interface RoleRepository extends BaseRepository<Role, String>
{
    Role findByRoleCode(String code);

    List<Role> findByRoleCodeNot(String code);
}