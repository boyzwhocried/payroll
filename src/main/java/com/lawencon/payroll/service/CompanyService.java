package com.lawencon.payroll.service;

import com.lawencon.payroll.dto.company.CompanyReqDto;
import com.lawencon.payroll.model.Company;
import com.lawencon.payroll.model.User;

public interface CompanyService {
    Company createCompany(CompanyReqDto data, User user);
}
