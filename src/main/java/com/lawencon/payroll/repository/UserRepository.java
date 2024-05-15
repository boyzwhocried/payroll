package com.lawencon.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.payroll.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
  User findByEmail(String email);
}