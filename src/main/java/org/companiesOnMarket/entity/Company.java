package org.companiesOnMarket.entity;

import jakarta.persistence.*;
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

    @Column(nullable = false, name = "created_at")
    private Instant createdAt;

    private String website;

    private String email;

    public Company() {}

    public Company(String name, String country, String symbol, Instant createdAt, String website, String email)
    {
        this.name = name;
        this.country = country;
        this.symbol = symbol;
        this.createdAt = createdAt;
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
    public void setCreatedAt(Instant createdAt) {this.createdAt = createdAt;}

    public String getWebsite() {return website;}
    public void setWebsite(String website) {this.website = website;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
}
