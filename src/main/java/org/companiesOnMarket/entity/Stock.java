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

    @Column(name = "company_id")
    private Long companyId;

    public Stock() {}

    public Stock(BigDecimal marketCapitalization, BigDecimal shareOutstanding)
    {
        this.marketCapitalization = marketCapitalization;
        this.shareOutstanding = shareOutstanding;
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

    protected void setCompanyId(Long companyId) { this.companyId = companyId; }
    public Long getCompanyId() { return companyId; }
}
