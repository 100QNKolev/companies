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

    public void synchronize() { em.flush(); }
}
