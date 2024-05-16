package com.lawencon.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lawencon.payroll.model.Company;

public interface CompanyRepository extends JpaRepository<Company, String> {
}
