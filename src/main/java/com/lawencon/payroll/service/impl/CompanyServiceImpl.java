package com.lawencon.payroll.service.impl;

import org.springframework.stereotype.Service;

import com.lawencon.payroll.dto.company.CompanyReqDto;
import com.lawencon.payroll.model.Company;
import com.lawencon.payroll.model.User;
import com.lawencon.payroll.repository.CompanyRepository;
import com.lawencon.payroll.service.CompanyService;
import com.lawencon.payroll.service.FileService;
import com.lawencon.payroll.service.PrincipalService;
import com.lawencon.payroll.util.DateUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    private final FileService fileService;
    private final PrincipalService principalService;

    @Override
    public Company createCompany(CompanyReqDto data, User user) {
        final String id = principalService.getUserId();
        
        final var file = fileService.saveFile(data.getFileContent(), data.getFileExtension());

        final var company = new Company();
        
        company.setCompanyName(data.getName());
        company.setClientId(user);
        company.setCompanyLogo(file);
        
        company.setPayrollDate(DateUtil.toLocalDateTime(data.getPayrollDate()));

        company.setCreatedBy(id);

        return companyRepository.save(company);
    }

}
