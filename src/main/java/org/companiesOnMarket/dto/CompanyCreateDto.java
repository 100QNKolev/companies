package org.companiesOnMarket.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CompanyCreateDto
{
    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 2, max = 2)
    private String country;

    @NotBlank
    private String symbol;

    @Email
    private String email;

    private String website;

    public CompanyCreateDto() {}

    public CompanyCreateDto(String name, String country, String symbol, String website, String email)
    {
        this.name = name;
        this.country = country;
        this.symbol = symbol;
        this.website = website;
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getWebsite() {
        return website;
    }
    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
