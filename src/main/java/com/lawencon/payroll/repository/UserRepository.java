package com.lawencon.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lawencon.payroll.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
  User findByEmail(String email);

  @Query(value = "SELECT cl FROM User cl "
              + "INNER JOIN Role r "
              + "ON cl.roleId = r.id "
              + "WHERE r.roleCode = :roleCode ")
  List<User> findByRoleRoleCode(@Param("roleCode") String roleCode);
  
  @Query(value = "SELECT cl FROM User cl "
              + "INNER JOIN ClientAssignment ca "
              + "ON ca.clientId = cl.id "
              + "WHERE cl.id IN "
              + "( "
              + "SELECT ca.clientId FROM ClientAssignment ca "
              + "INNER JOIN User cl "
              + "ON cl.id = ca.clientId "
              + "INNER JOIN User ps "
              + "ON ps.id = ca.psId "
              + "WHERE ps.id = :psId "
              + ") ")
  List<User> findAllById(@Param("psId") String psId);

  @Query(value = "SELECT cl FROM User cl "
              + "INNER JOIN Role rl "
              + "ON cl.roleId.id = rl.id "
              + "WHERE rl.roleCode = :roleCode AND cl.id NOT IN "
              + "( "
              + "SELECT ca.clientId FROM ClientAssignment ca "
              + "WHERE ca.psId.id = :psId "
              + ") ")
  List<User> findAllByIdNot(@Param("psId") String psId, @Param("roleCode") String roleCode);
}