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
    CompanyRepository repo;

    @Inject
    CompanyMapper mapper;

    public List<CompanyGetDto> getAllCompanies()
    {
        List<Company> companies = repo.getAll();
        return mapper.toGetCompanyDtoList(companies);
    }

    public Company getCompanyById(long id) { return repo.findById(id); }

    @Transactional
    public void createCompany(CompanyCreateDto companyDto)
    {
        // TODO: Add authorization
        Company company = mapper.fromCreateDto(companyDto);

        try {
            repo.create(company);
        } catch (ConstraintViolationException e) {
            throw new PersistenceException("Database validation failed");
        }
    }

    @Transactional
    public CompanyGetDto updateCompany(long id, CompanyUpdateDto companyDto)
    {
        Company existingCompany = repo.findById(id);

        if (existingCompany == null)
        {
            throw new NotFoundException("Company not found");
        }

        mapper.updateEntityFromDto(companyDto, existingCompany);

        try {
            repo.synchronize();
        } catch (ConstraintViolationException e) {
            throw new PersistenceException("Database validation failed");
        }
        CompanyGetDto result = new CompanyGetDto();
        mapper.createGetCompanyResultDto(existingCompany, result);
        return result;
    }
}
