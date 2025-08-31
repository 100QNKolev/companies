package org.companiesOnMarket.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.companiesOnMarket.dto.StockGetDto;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/stock/profile2")
@RegisterRestClient(configKey = "stock-api")
public interface StockApiClient {

    @GET
    StockGetDto getBySymbol(@QueryParam("symbol") String symbol);
}
