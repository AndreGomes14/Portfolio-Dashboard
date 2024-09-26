package com.InvestmentsTracker.investment_portfolio.model;

import jakarta.persistence.DiscriminatorValue;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@DiscriminatorValue("OTHER")
public class Other extends Investment{

    String description;
    @Override
    public double getCurrentMarketPrice() {
        return 0;
    }

    @Override
    public String getInvestmentType() {
        return "OTHER";
    }
}
