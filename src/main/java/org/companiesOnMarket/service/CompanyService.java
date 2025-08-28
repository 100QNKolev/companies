package org.companiesOnMarket.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.companiesOnMarket.entity.Company;
import org.companiesOnMarket.repository.CompanyRepository;

import java.time.Instant;
import java.util.List;

@ApplicationScoped
public class CompanyService {

    @Inject
    CompanyRepository repo;

    public List<Company> getAllCompanies() { return repo.getAll(); }

    public void createCompany(Company company)
    {
        // TODO: Add authorization
        company.setCreatedAt(Instant.now());
        repo.create(company);
    }
}
