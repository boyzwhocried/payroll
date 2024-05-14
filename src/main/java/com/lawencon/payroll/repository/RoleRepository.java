package com.lawencon.payroll.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.payroll.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>
{
  List<Role> getAll(Role role);
  Optional<Role> findById(String id);
}