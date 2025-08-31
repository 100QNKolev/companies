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
    private Long marketCapitalization;

    @Column(nullable = false, name = "share_outstanting")
    private BigDecimal shareOutstanding;

    @UpdateTimestamp
    @Column(nullable = false, name = "last_fetched")
    private Instant lastFetched;

    public Stock() {}

    public Stock(Long marketCapitalization, BigDecimal shareOutstanding)
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

    public Long getMarketCapitalization() {
        return marketCapitalization;
    }
    public void setMarketCapitalization(Long marketCapitalization) {
        this.marketCapitalization = marketCapitalization;
    }

    public BigDecimal getShareOutstanding() { return shareOutstanding; }
    public void setShareOutstanding(BigDecimal shareOutstanding) { this.shareOutstanding = shareOutstanding; }

    public Instant getLastFetched() { return lastFetched; }
}
