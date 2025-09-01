package org.companiesOnMarket.repository;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.companiesOnMarket.entity.Company;
import org.companiesOnMarket.entity.Stock;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class StockRepositoryTest {

    @Inject
    StockRepository stockRepo;

    @Inject
    EntityManager em;

    @Test
    @TestTransaction
    void createAndFindSingleStock() {
        Company tempCompany = new Company("FlushCo", "BG", "FLH", null, null, Instant.now());
        em.persist(tempCompany);
        em.flush();

        Long companyId = tempCompany.getId();

        Stock stock = new Stock(new BigDecimal(1000), new BigDecimal(500));
        stock.setCompanyId(companyId);

        stockRepo.create(stock);
        em.flush();

        Stock found = stockRepo.findLatestByCompanyId(companyId);
        assertNotNull(found);
        assertEquals(companyId, found.getCompanyId());
        assertEquals(new BigDecimal(1000), found.getMarketCapitalization());
        assertEquals(new BigDecimal(500), found.getShareOutstanding());
    }

    @Test
    @TestTransaction
    void findLatestByCompanyIdReturnsLatest() {
        Company tempCompany = new Company("FlushCo", "BG", "FLH", null, null, Instant.now());
        em.persist(tempCompany);
        em.flush();

        Long companyId = tempCompany.getId();

        Stock oldStock = new Stock(new BigDecimal("2000.00"), new BigDecimal("1000"));
        oldStock.setCompanyId(companyId);
        oldStock.setLastFetched(Instant.now().minusSeconds(60));
        stockRepo.create(oldStock);

        Stock latestStock = new Stock(new BigDecimal("3000.00"), new BigDecimal("1500"));
        latestStock.setCompanyId(companyId);
        latestStock.setLastFetched(Instant.now());
        stockRepo.create(latestStock);

        em.flush();

        Stock found = stockRepo.findLatestByCompanyId(companyId);
        assertNotNull(found);
        assertEquals(new BigDecimal("3000.00"), found.getMarketCapitalization());
        assertEquals(new BigDecimal("1500"), found.getShareOutstanding());
    }

    @Test
    @TestTransaction
    void findLatestByCompanyIdReturnsNullIfNoStocks() {
        Stock found = stockRepo.findLatestByCompanyId(999L);
        assertNull(found);
    }
}