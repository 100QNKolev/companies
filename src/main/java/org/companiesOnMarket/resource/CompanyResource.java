package org.companiesOnMarket.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import org.companiesOnMarket.dto.CompanyCreateDto;
import org.companiesOnMarket.dto.CompanyGetDto;
import org.companiesOnMarket.dto.CompanyUpdateDto;
import org.companiesOnMarket.service.CompanyService;

import java.util.List;

@Path("/companies")
public class CompanyResource {

    @Inject
    CompanyService companyService;

    @GET
    @Path("/")
    public List<CompanyGetDto> getAllCompanies() { return companyService.getAllCompanies(); }

    @POST
    @Path("/")
    public void createCompany(@Valid CompanyCreateDto newCompany) { companyService.createCompany(newCompany); }

    @PUT
    @Path("/{id}")
    public CompanyGetDto updateCompany(@PathParam("id") long id, @Valid CompanyUpdateDto company) { return companyService.updateCompany(id, company); }
}
