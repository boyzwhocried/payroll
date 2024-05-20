package com.lawencon.payroll.service;

import java.util.List;

import com.lawencon.payroll.dto.company.CompanyReqDto;
import com.lawencon.payroll.dto.company.CompanyResDto;
import com.lawencon.payroll.model.Company;
import com.lawencon.payroll.model.User;

public interface CompanyService {
    Company createCompany(CompanyReqDto data, User user);

    List<CompanyResDto> getCompanies();
}
