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
import org.companiesOnMarket.error.StockApiFetchException;
import org.companiesOnMarket.mapper.StockMapper;
import org.companiesOnMarket.repository.StockRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@ApplicationScoped
public class StockService {

    @Inject
    @RestClient
    StockApiClient stockApiClient;

    @Inject
    CompanyService companyService;

    @Inject
    StockRepository stockRepo;

    @Inject
    StockMapper mapper;

    @Transactional
    public CompanyGetStocksDto getOrUpdateStock(long companyId) {
        Company company = companyService.getCompanyById(companyId);

        if (company == null) {
            throw new NotFoundException("Company not found");
        }

        Instant isOld = Instant.now().minus(24, ChronoUnit.HOURS);

        if (company.getLastFetched() != null && company.getLastFetched().isAfter(isOld)) {
            Stock latest = stockRepo.findLatestByCompanyId(companyId);
            return buildDto(company, latest);
        }

        Stock latest = stockRepo.findLatestByCompanyId(companyId);

        if (latest != null && latest.getLastFetched().isAfter(isOld)) {
            company.setLastFetched(latest.getLastFetched());
            return buildDto(company, latest);
        }

        StockGetDto stockDto;
        try {
            stockDto = stockApiClient.getBySymbol(company.getSymbol());
        } catch (Exception e) {
            throw new StockApiFetchException("Failed to fetch stock for company " + company.getName());
        }

        Stock newStock = new Stock();
        mapper.updateEntityFromDto(stockDto, newStock);
        newStock.setLastFetched(Instant.now());
        newStock.setCompany(company);

        try {
            stockRepo.create(newStock);
        } catch (Exception e) {
            throw new PersistenceException("Failed to save stock for company " + company.getName());
        }

        company.addStock(newStock);
        company.setLastFetched(newStock.getLastFetched());

        return buildDto(company, newStock);
    }

    private CompanyGetStocksDto buildDto(Company company, Stock stock) {
        CompanyGetStocksDto dto = new CompanyGetStocksDto();
        mapper.createGetCompanyStockResult(company, stock, dto);
        return dto;
    }
}