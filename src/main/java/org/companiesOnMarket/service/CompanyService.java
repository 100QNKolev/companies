package org.companiesOnMarket.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.companiesOnMarket.entity.Company;
import org.companiesOnMarket.error.NotFoundException;
import org.companiesOnMarket.repository.CompanyRepository;
import org.companiesOnMarket.util.CompanyValidator;

import java.time.Instant;
import java.util.List;

@ApplicationScoped
public class CompanyService {

    @Inject
    CompanyRepository repo;

    public List<Company> getAllCompanies() { return repo.getAll(); }

    @Transactional
    public void createCompany(Company company)
    {
        // TODO: Add authorization
        company.setCreatedAt(Instant.now());

        CompanyValidator.validateInput(company);

        repo.create(company);
    }

    @Transactional
    public Company updateCompany(long id, Company companyToUpdate)
    {
        Company existingCompany = repo.findById(id);

        if (existingCompany == null)
        {
            throw new NotFoundException("Company not found");
        }

        //CompanyValidator.validateInput(companyToUpdate);

        if (companyToUpdate.getName() != null && !companyToUpdate.getName().isBlank()) {
            existingCompany.setName(companyToUpdate.getName());
        }
        if (companyToUpdate.getCountry() != null && !companyToUpdate.getCountry().isBlank()) {
            existingCompany.setCountry(companyToUpdate.getCountry());
        }
        if (companyToUpdate.getSymbol() != null && !companyToUpdate.getSymbol().isBlank()) {
            existingCompany.setSymbol(companyToUpdate.getSymbol());
        }
        if (companyToUpdate.getEmail() != null) {
            existingCompany.setEmail(companyToUpdate.getEmail());
        }
        if (companyToUpdate.getWebsite() != null) {
            existingCompany.setWebsite(companyToUpdate.getWebsite());
        }

        return existingCompany;
    }
}
