package org.companiesOnMarket.dto;

import java.math.BigDecimal;

public class CompanyGetStocksDto {
    private String name;

    private String country;

    private String symbol;

    private String email;

    private String website;

    private Long marketCapitalization;

    private BigDecimal shareOutstanding;

    public CompanyGetStocksDto() {}

    public CompanyGetStocksDto(String name, String country, String symbol, String website, String email, Long marketCapitalization, BigDecimal shareOutstanding)
    {
        this.name = name;
        this.country = country;
        this.symbol = symbol;
        this.website = website;
        this.email = email;
        this.marketCapitalization = marketCapitalization;
        this.shareOutstanding = shareOutstanding;
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

    public Long getMarketCapitalization() {
        return marketCapitalization;
    }
    public void setMarketCapitalization(Long marketCapitalization) {
        this.marketCapitalization = marketCapitalization;
    }

    public BigDecimal getShareOutstanding() { return shareOutstanding; }
    public void setShareOutstanding(BigDecimal shareOutstanding) { this.shareOutstanding = shareOutstanding; }
}