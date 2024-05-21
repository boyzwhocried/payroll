package com.lawencon.payroll.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.lawencon.payroll.dto.company.CompanyReqDto;
import com.lawencon.payroll.dto.company.CompanyResDto;
import com.lawencon.payroll.dto.company.UpdateCompanyReqDto;
import com.lawencon.payroll.dto.generalResponse.UpdateResDto;
import com.lawencon.payroll.model.Company;
import com.lawencon.payroll.repository.CompanyRepository;
import com.lawencon.payroll.service.CompanyService;
import com.lawencon.payroll.service.FileService;
import com.lawencon.payroll.service.PrincipalService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    private final FileService fileService;
    private final PrincipalService principalService;

    @Override
    @Transactional
    public Company createCompany(CompanyReqDto data) {
        final String id = principalService.getUserId();
        
        final var file = fileService.saveFile(data.getFileContent(), data.getFileExtension());

        final var company = new Company();
        
        company.setCompanyName(data.getCompanyName());
        company.setCompanyLogo(file);
        
        company.setPayrollDate(Integer.valueOf(data.getPayrollDate()));

        company.setCreatedBy(id);

        return companyRepository.save(company);
    }

    @Override
    public Company findByCompanyName(String companyName) {
        return companyRepository.findByCompanyName(companyName);
    }

    @Override
    public List<CompanyResDto> getCompanies() {
        final var companiesRes = new ArrayList<CompanyResDto>();
        
        final var companies = companyRepository.findAll();

        companies.forEach(company -> {
            final var companyRes = new CompanyResDto();

            companyRes.setId(company.getId());
            companyRes.setCompanyName(company.getCompanyName());
            companyRes.setCompanyLogoId(company.getCompanyLogo().getId());
            
            companiesRes.add(companyRes);
        });

        return companiesRes;
    }

    @Override
    @Transactional
    public UpdateResDto updateCompany(UpdateCompanyReqDto data) {
        var company = companyRepository.findById(data.getId()).get();

        var name = Optional.ofNullable(data.getCompanyName());
        if (name.isPresent()) {
            company.setCompanyName(name.get());
            company.setUpdatedBy(principalService.getUserId());
        }

        var content = Optional.ofNullable(data.getCompanyLogoContent());
        if (content.isPresent()) {
            var file = company.getCompanyLogo();
            file.setFileContent(content.get());
            file.setFileExtension(data.getCompanyLogoExtension());

            file = fileService.updateFile(file);

            company.setCompanyLogo(file);
        }

        company = companyRepository.saveAndFlush(company);

        final var updateRes = new UpdateResDto();

        if (name.isPresent()) {
            updateRes.setVersion(company.getVer());
        }
        
        updateRes.setMessage("Company data has been updated!");

        return updateRes;
    }

}
