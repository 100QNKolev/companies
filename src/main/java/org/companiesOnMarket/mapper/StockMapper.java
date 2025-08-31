package org.companiesOnMarket.mapper;

import org.companiesOnMarket.dto.CompanyGetStocksDto;
import org.companiesOnMarket.dto.StockGetDto;
import org.companiesOnMarket.entity.Company;
import org.companiesOnMarket.entity.Stock;
import org.mapstruct.*;

@Mapper(componentModel = "cdi")
public interface StockMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(StockGetDto dto, @MappingTarget Stock entity);

    @Mapping(source = "companyStock.marketCapitalization", target = "marketCapitalization")
    @Mapping(source = "companyStock.shareOutstanding", target = "shareOutstanding")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void createGetCompanyStockResult(Company entity, @MappingTarget CompanyGetStocksDto dto);
}
