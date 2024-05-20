package com.lawencon.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lawencon.payroll.model.ClientAssignment;

@Repository
public interface ClientAssignmentRepository extends JpaRepository<ClientAssignment, String>
{   
    @Query(value = "SELECT COUNT(ca.clientId) "
                + "FROM ClientAssignment ca "
                + "WHERE ca.psId.id = :psId ")
    Integer getCountClientIdByPsId(@Param("psId") String id);
}