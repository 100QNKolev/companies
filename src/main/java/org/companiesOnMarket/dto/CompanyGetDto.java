package org.companiesOnMarket.dto;

import java.time.Instant;

public class CompanyGetDto {

    private Long id;

    private String name;

    private String country;

    private String symbol;

    private Instant createdAt;

    private String email;

    private String website;

    public CompanyGetDto() {}

    public CompanyGetDto(Long id, String name, String country, String symbol,Instant createdAt, String website, String email)
    {
        this.id = id;
        this.name = name;
        this.country = country;
        this.symbol = symbol;
        this.createdAt = createdAt;
        this.website = website;
        this.email = email;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
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
