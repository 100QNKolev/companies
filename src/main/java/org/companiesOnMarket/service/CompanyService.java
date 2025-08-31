package org.companiesOnMarket.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.companiesOnMarket.dto.CompanyCreateDto;
import org.companiesOnMarket.dto.CompanyGetDto;
import org.companiesOnMarket.dto.CompanyUpdateDto;
import org.companiesOnMarket.entity.Company;
import org.companiesOnMarket.error.NotFoundException;
import org.companiesOnMarket.error.PersistenceException;
import org.companiesOnMarket.mapper.CompanyMapper;
import org.companiesOnMarket.repository.CompanyRepository;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

@ApplicationScoped
public class CompanyService {

    @Inject
    CompanyRepository companyRepo;

    @Inject
    CompanyMapper mapper;

    public List<CompanyGetDto> getAllCompanies()
    {
        List<Company> companies = companyRepo.getAll();
        return mapper.toGetCompanyDtoList(companies);
    }

    public Company getCompanyById(long id) { return companyRepo.findById(id); }

    @Transactional
    public void createCompany(CompanyCreateDto companyDto)
    {
        // TODO: Add authorization
        Company company = mapper.fromCreateDto(companyDto);

        try {
            companyRepo.create(company);
        } catch (Exception e) {
            throw new PersistenceException("Failed to save company " + company.getName());
        }
    }

    @Transactional
    public CompanyGetDto updateCompany(long id, CompanyUpdateDto companyDto)
    {
        Company existingCompany = companyRepo.findById(id);

        if (existingCompany == null)
        {
            throw new NotFoundException("Company not found");
        }

        mapper.updateEntityFromDto(companyDto, existingCompany);

        try {
            companyRepo.synchronize();
        } catch (Exception e) {
            throw new PersistenceException("Failed to update company " + existingCompany.getName());
        }

        CompanyGetDto result = new CompanyGetDto();
        mapper.createGetCompanyResultDto(existingCompany, result);
        return result;
    }
}
