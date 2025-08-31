package org.companiesOnMarket.dto;

import java.math.BigDecimal;

public class StockGetDto {
    private Long marketCapitalization;

    private BigDecimal shareOutstanding;

    public StockGetDto() {}

    public StockGetDto(Long marketCapitalization, BigDecimal shareOutstanding)
    {
        this.marketCapitalization = marketCapitalization;
        this.shareOutstanding = shareOutstanding;
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
