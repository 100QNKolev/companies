package org.companiesOnMarket.mapper;

import org.companiesOnMarket.dto.CompanyCreateDto;
import org.companiesOnMarket.dto.CompanyGetDto;
import org.companiesOnMarket.dto.CompanyUpdateDto;
import org.companiesOnMarket.entity.Company;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import java.util.List;

@Mapper(componentModel = "cdi")
public interface CompanyMapper {

    Company fromCreateDto(CompanyCreateDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(CompanyUpdateDto dto, @MappingTarget Company entity);

    void createGetCompanyResultDto(Company entity, @MappingTarget CompanyGetDto dto);

    List<CompanyGetDto> toGetCompanyDtoList(List<Company> companies);
}
