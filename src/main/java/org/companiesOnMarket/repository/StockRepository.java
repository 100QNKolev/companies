package org.companiesOnMarket.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.companiesOnMarket.entity.Stock;

@ApplicationScoped
public class StockRepository {

    @Inject
    EntityManager em;

    public void create(Stock stock) { em.persist(stock); }

    public Stock findLatestByCompanyId(Long companyId) {
        return em.createQuery(
                "SELECT s FROM Stock s WHERE s.company.id = :companyId ORDER BY s.lastFetched DESC", Stock.class)
                .setParameter("companyId", companyId)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }
}
