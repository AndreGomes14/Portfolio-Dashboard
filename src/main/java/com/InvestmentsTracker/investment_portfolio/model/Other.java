package com.InvestmentsTracker.investment_portfolio.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class Other extends Investment{

    String description;
    @Override
    public double getCurrentMarketPrice() {
        return 0;
    }
}
