package com.lawencon.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.payroll.model.DocumentType;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, String> {

}
