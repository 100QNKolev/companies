package org.companiesOnMarket.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "companies", schema = "public")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 2)
    private String country;

    @Column(nullable = false, unique = true)
    private String symbol;

    @CreationTimestamp
    @Column(nullable = false, name = "created_at")
    private Instant createdAt;

    private String website;

    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stocks_id")
    private Stock companyStock;

    public Company() {}

    public Company(String name, String country, String symbol, String website, String email)
    {
        this.name = name;
        this.country = country;
        this.symbol = symbol;
        this.website = website;
        this.email = email;
    }

    public Long getId() {return id;}
    protected void setId(Long id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getCountry() {return country;}
    public void setCountry(String country) {this.country = country;}

    public String getSymbol() {return symbol;}
    public void setSymbol(String symbol) {this.symbol = symbol;}

    public Instant getCreatedAt() {return createdAt;}

    public String getWebsite() {return website;}
    public void setWebsite(String website) {this.website = website;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public Stock getCompanyStock() {
        return companyStock;
    }
    public void setCompanyStock(Stock companyStock) {
        this.companyStock = companyStock;
    }
}
