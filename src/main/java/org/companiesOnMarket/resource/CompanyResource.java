package org.companiesOnMarket.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.companiesOnMarket.entity.Company;
import org.companiesOnMarket.service.CompanyService;

import java.util.List;

@Path("/companies")
public class CompanyResource {

    @Inject
    CompanyService companyService;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Company> getAllCompanies() { return companyService.getAllCompanies(); }

    @POST
    @Path("/")
    public void createCompany(Company newCompany) { companyService.createCompany(newCompany); }
}
