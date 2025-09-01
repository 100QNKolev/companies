package org.companiesOnMarket.service;

import io.quarkus.test.junit.QuarkusTest;
import org.companiesOnMarket.client.StockApiClient;
import org.companiesOnMarket.dto.CompanyGetStocksDto;
import org.companiesOnMarket.dto.StockGetDto;
import org.companiesOnMarket.entity.Company;
import org.companiesOnMarket.entity.Stock;
import org.companiesOnMarket.error.NotFoundException;
import org.companiesOnMarket.error.PersistenceException;
import org.companiesOnMarket.mapper.StockMapper;
import org.companiesOnMarket.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@QuarkusTest
class StockServiceTest {

    StockService stockService;

    @Mock
    StockApiClient stockApiClient;

    @Mock
    CompanyService companyService;

    @Mock
    StockRepository stockRepo;

    @Mock
    StockMapper mapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        stockService = new StockService(stockApiClient, companyService, stockRepo, mapper);
    }

    @Test
    void getOrUpdateStockReturnsLatestIfCompanyFresh() {
        Company company = new Company("FreshCo", "US", "FRSH", null, null, Instant.now());
        company.setLastFetched(Instant.now());

        Stock latestStock = new Stock(new BigDecimal("1000"), new BigDecimal("500"));
        latestStock.setCompanyId(1L);

        when(companyService.getCompanyById(1L)).thenReturn(company);
        when(stockRepo.findLatestByCompanyId(1L)).thenReturn(latestStock);

        doAnswer(invocation -> {
            CompanyGetStocksDto dtoArg = invocation.getArgument(2);
            dtoArg.setName(company.getName());
            return null;
        }).when(mapper).createGetCompanyStockResult(eq(company), eq(latestStock), any(CompanyGetStocksDto.class));

        CompanyGetStocksDto result = stockService.getOrUpdateStock(1L);

        assertEquals("FreshCo", result.getName());
    }


    @Test
    void getOrUpdateStockFetchesFromApiIfCompanyOld() {
        Company company = new Company("OldCo", "US", "OLD", null, null, Instant.now().minus(48, ChronoUnit.HOURS));
        when(companyService.getCompanyById(2L)).thenReturn(company);

        StockGetDto apiDto = new StockGetDto();
        apiDto.setMarketCapitalization(new BigDecimal("2000"));
        apiDto.setShareOutstanding(new BigDecimal("1000"));

        when(stockApiClient.getBySymbol("OLD")).thenReturn(apiDto);

        doAnswer(inv -> {
            Stock stock = inv.getArgument(1);
            stock.setMarketCapitalization(apiDto.getMarketCapitalization());
            stock.setShareOutstanding(apiDto.getShareOutstanding());
            return null;
        }).when(mapper).updateEntityFromDto(eq(apiDto), any(Stock.class));

        doNothing().when(stockRepo).create(any(Stock.class));

        doAnswer(invocation -> {
            CompanyGetStocksDto dtoArg = invocation.getArgument(2);
            dtoArg.setName(company.getName());
            return null;
        }).when(mapper).createGetCompanyStockResult(eq(company), any(Stock.class), any(CompanyGetStocksDto.class));

        CompanyGetStocksDto result = stockService.getOrUpdateStock(2L);

        assertEquals("OldCo", result.getName());
    }


    @Test
    void getOrUpdateStockThrowsNotFoundIfCompanyMissing() {
        when(companyService.getCompanyById(999L)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> stockService.getOrUpdateStock(999L));
    }

    @Test
    void getOrUpdateStockThrowsPersistenceExceptionOnRepoFailure() {
        Company company = new Company("ErrCo", "US", "ERR", null, null, Instant.now().minus(48, ChronoUnit.HOURS));
        when(companyService.getCompanyById(3L)).thenReturn(company);

        StockGetDto apiDto = new StockGetDto();
        apiDto.setMarketCapitalization(new BigDecimal("5000"));
        apiDto.setShareOutstanding(new BigDecimal("2500"));
        when(stockApiClient.getBySymbol("ERR")).thenReturn(apiDto);

        doAnswer(inv -> {
            Stock stock = inv.getArgument(1);
            stock.setMarketCapitalization(apiDto.getMarketCapitalization());
            stock.setShareOutstanding(apiDto.getShareOutstanding());
            return null;
        }).when(mapper).updateEntityFromDto(eq(apiDto), any(Stock.class));

        doThrow(new RuntimeException("DB fail")).when(stockRepo).create(any(Stock.class));

        assertThrows(PersistenceException.class, () -> stockService.getOrUpdateStock(3L));
    }
}