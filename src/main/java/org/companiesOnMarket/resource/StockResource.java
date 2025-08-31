package org.companiesOnMarket.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.companiesOnMarket.dto.CompanyGetStocksDto;
import org.companiesOnMarket.service.CompanyService;
import org.companiesOnMarket.service.StockService;

@Path("/company-stocks")
public class StockResource {

    private final StockService stockService;

    @Inject
    public StockResource(StockService stockService)
    {
        this.stockService = stockService;
    }

    @GET
    @Path("/{id}")
    public CompanyGetStocksDto getCompanyStocks(@PathParam("id") long id) { return stockService.getOrUpdateStock(id); }
}
