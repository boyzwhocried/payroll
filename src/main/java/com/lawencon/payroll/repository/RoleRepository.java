package com.lawencon.payroll.repository;

import org.springframework.stereotype.Repository;

import com.lawencon.payroll.model.Role;

@Repository
public interface RoleRepository extends BaseRepository<Role, String>
{
}