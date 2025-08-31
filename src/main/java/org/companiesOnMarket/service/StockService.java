package org.companiesOnMarket.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.companiesOnMarket.client.StockApiClient;
import org.companiesOnMarket.dto.CompanyGetStocksDto;
import org.companiesOnMarket.dto.StockGetDto;
import org.companiesOnMarket.entity.Company;
import org.companiesOnMarket.entity.Stock;
import org.companiesOnMarket.error.NotFoundException;
import org.companiesOnMarket.error.PersistenceException;
import org.companiesOnMarket.mapper.StockMapper;
import org.companiesOnMarket.repository.StockRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.hibernate.exception.ConstraintViolationException;

@ApplicationScoped
public class StockService {

    @Inject
    @RestClient
    StockApiClient stockApiClient;

    @Inject
    CompanyService companyService;

    @Inject
    StockRepository repo;

    @Inject
    StockMapper mapper;

    @Transactional
    public CompanyGetStocksDto getOrCreateStockById(long id)
    {
        Company company = companyService.getCompanyById(id);

        if (company == null)
        {
            throw new NotFoundException("Company not found");
        }

        Stock stock = company.getCompanyStock();

        if (stock == null)
        {
            stock = new Stock();
            StockGetDto newStock = stockApiClient.getBySymbol(company.getSymbol());
            mapper.updateEntityFromDto(newStock, stock);
        }

        try {
            repo.create(stock);
        } catch (ConstraintViolationException e) {
            throw new PersistenceException("Database validation failed");
        }

        company.setCompanyStock(stock);

        try {
            repo.synchronize();
        } catch (ConstraintViolationException e) {
            throw new PersistenceException("Database validation failed");
        }

        CompanyGetStocksDto companyGetStocksDto = new CompanyGetStocksDto();
        mapper.createGetCompanyStockResult(company, companyGetStocksDto);
        return companyGetStocksDto;
    }

}
