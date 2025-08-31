package org.companiesOnMarket.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false, unique = true, length = 10)
    private String symbol;

    @CreationTimestamp
    @Column(nullable = false, name = "created_at")
    private Instant createdAt;

    private String website;

    private String email;

    @Column(name = "last_fetched")
    private Instant lastFetched;

    @JoinColumn(name = "company_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stock> stocks = new ArrayList<>();

    public Company() {}

    public Company(String name, String country, String symbol, String website, String email, Instant lastFetched)
    {
        this.name = name;
        this.country = country;
        this.symbol = symbol;
        this.website = website;
        this.email = email;
        this.lastFetched = lastFetched;
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

    public Instant getLastFetched() { return lastFetched; }
    public void setLastFetched(Instant lastFetched) { this.lastFetched = lastFetched; }

    public List<Stock> getStocks() { return stocks; }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public void removeStock(Stock stock) {
        stocks.remove(stock);
    }
}
