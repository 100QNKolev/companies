package org.companiesOnMarket.dto;

import java.math.BigDecimal;

public class StockGetDto {
    private BigDecimal marketCapitalization;

    private BigDecimal shareOutstanding;

    public StockGetDto() {}

    public StockGetDto(BigDecimal marketCapitalization, BigDecimal shareOutstanding)
    {
        this.marketCapitalization = marketCapitalization;
        this.shareOutstanding = shareOutstanding;
    }

    public BigDecimal getMarketCapitalization() {
        return marketCapitalization;
    }
    public void setMarketCapitalization(BigDecimal marketCapitalization) {
        this.marketCapitalization = marketCapitalization;
    }

    public BigDecimal getShareOutstanding() { return shareOutstanding; }
    public void setShareOutstanding(BigDecimal shareOutstanding) { this.shareOutstanding = shareOutstanding; }
}
