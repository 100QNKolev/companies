package org.companiesOnMarket.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.companiesOnMarket.dto.CompanyCreateDto;
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

    public List<Company> getAllCompanies() { return repo.getAll(); }

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
    public Company updateCompany(long id, CompanyUpdateDto companyDto)
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

        return existingCompany;
    }
}
