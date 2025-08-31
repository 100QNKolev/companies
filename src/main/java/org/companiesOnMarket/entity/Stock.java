package org.companiesOnMarket.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "stock", schema = "public")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "market_capitalization")
    private BigDecimal marketCapitalization;

    @Column(nullable = false, name = "share_outstanding")
    private BigDecimal shareOutstanding;

    @UpdateTimestamp
    @Column(nullable = false, name = "last_fetched")
    private Instant lastFetched;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    public Stock() {}

    public Stock(BigDecimal marketCapitalization, BigDecimal shareOutstanding, Company company)
    {
        this.marketCapitalization = marketCapitalization;
        this.shareOutstanding = shareOutstanding;
        this.company = company;
    }

    protected void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public BigDecimal getMarketCapitalization() {
        return marketCapitalization;
    }
    public void setMarketCapitalization(BigDecimal marketCapitalization) { this.marketCapitalization = marketCapitalization; }

    public BigDecimal getShareOutstanding() { return shareOutstanding; }
    public void setShareOutstanding(BigDecimal shareOutstanding) { this.shareOutstanding = shareOutstanding; }

    public Instant getLastFetched() { return lastFetched; }
    public void setLastFetched(Instant lastFetched) { this.lastFetched = lastFetched; }

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }
}
