package com.InvestmentsTracker.investment_portfolio.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class Crypto extends Investment{

    String ticker;
    String exchange;
    Double lastSyncedPrice;

    @Override
    public double getCurrentMarketPrice() {
        return 0;
    }
}
