package com.lawencon.payroll.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.payroll.model.ClientAssignment;

@Repository
public interface ClientAssignmentRepository extends JpaRepository<ClientAssignment, String>
{
  List<ClientAssignment> getAll(ClientAssignment clientAssignment);
  Optional<ClientAssignment> findById();
}