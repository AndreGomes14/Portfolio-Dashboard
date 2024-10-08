package com.InvestmentsTracker.investment_portfolio.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Getter
@Setter
@Slf4j
@DiscriminatorValue("SAVINGS")
public class Savings extends Investment{

    private Double totalDeposit;
    private String description;

    @Override
    public double getCurrentMarketPrice() {
        return 0;
    }

    @Override
    public String getInvestmentType() {
        return "SAVINGS";
    }

}
