package com.InvestmentsTracker.investment_portfolio.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class Stock extends Investment {

    String ticker;
    Double divideYield;
    String exchange;
    @Override
    public double getCurrentMarketPrice() {
        return 0;
    }
}
