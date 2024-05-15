package com.lawencon.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.payroll.model.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, String> {

}
