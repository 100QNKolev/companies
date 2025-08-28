package org.companiesOnMarket.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.companiesOnMarket.entity.Company;
import java.util.List;

@ApplicationScoped
public class CompanyRepository {

    @Inject
    EntityManager em;

    public void create(Company company) { em.persist(company); }

    public List<Company> getAll()
    {
        TypedQuery<Company> query = em.createQuery("from Company", Company.class);
        return query.getResultList();
    }

    public Company findById(long id) { return em.find(Company.class, id); }

}
