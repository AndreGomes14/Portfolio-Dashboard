package com.InvestmentsTracker.investment_portfolio.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import jakarta.persistence.DiscriminatorValue;

@Getter
@Setter
@Slf4j
@DiscriminatorValue("CRYPTO")
public class Crypto extends Investment{

    String ticker;
    String exchange;
    Double lastSyncedPrice;

    @Override
    public double getCurrentMarketPrice() {
        return 0;
    }
    @Override
    public String getInvestmentType() {
        return "CRYPTO";
    }
}
