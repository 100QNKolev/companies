package org.companiesOnMarket.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.companiesOnMarket.entity.Stock;
import org.companiesOnMarket.service.StockService;

@ApplicationScoped
public class StockRepository {

    private final EntityManager em;

    @Inject
    public StockRepository(EntityManager em)
    {
        this.em = em;
    }

    public void create(Stock stock) { em.persist(stock); }

    public Stock findLatestByCompanyId(Long companyId) {
        return em.createQuery(
                "SELECT s FROM Stock s WHERE s.companyId = :companyId ORDER BY s.lastFetched DESC", Stock.class)
                .setParameter("companyId", companyId)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }
}
