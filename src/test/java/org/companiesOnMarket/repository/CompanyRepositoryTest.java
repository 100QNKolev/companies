package org.companiesOnMarket.repository;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.companiesOnMarket.entity.Company;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class CompanyRepositoryTest {

    public final CompanyRepository companyRepo;
    public final EntityManager em;

    public CompanyRepositoryTest(CompanyRepository companyRepo, EntityManager em)
    {
        this.companyRepo = companyRepo;
        this.em = em;
    }

    @Test
    @TestTransaction
    void createAndFindById() {
        Company company = new Company(
                "TestCo",
                "US",
                "TST",
                "https://test.com",
                "info@test.com",
                Instant.now()
        );

        companyRepo.create(company);
        companyRepo.synchronize();

        assertNotNull(company.getId());

        Company found = companyRepo.findById(company.getId());
        assertNotNull(found);
        assertEquals("TestCo", found.getName());
    }

    @Test
    @TestTransaction
    void getAll() {
        Company c1 = new Company("CompA", "US", "CMPA", null, null, Instant.now());
        Company c2 = new Company("CompB", "DE", "CMPB", null, null, Instant.now());

        companyRepo.create(c1);
        companyRepo.create(c2);
        companyRepo.synchronize();

        List<Company> all = companyRepo.getAll();
        assertTrue(all.size() >= 2);
        assertTrue(all.stream().anyMatch(c -> c.getSymbol().equals("CMPA")));
        assertTrue(all.stream().anyMatch(c -> c.getSymbol().equals("CMPB")));
    }

    @Test
    @TestTransaction
    void synchronize() {
        Company company = new Company("FlushCo", "BG", "FLH", null, null, Instant.now());

        companyRepo.create(company);
        companyRepo.synchronize();

        assertNotNull(company.getId());
    }
}