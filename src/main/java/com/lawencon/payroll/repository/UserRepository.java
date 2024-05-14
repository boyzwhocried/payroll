package com.lawencon.payroll.repository;

import org.springframework.stereotype.Repository;

import com.lawencon.payroll.model.User;

@Repository
public interface UserRepository extends BaseRepository<User, String>{
  User findByEmail(String email);
}