package com.InvestmentsTracker.investment_portfolio.dto.investment;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public abstract class InvestmentResponseDTO {

    private UUID id;
    private double buyPrice;
    private String date;
    private int riskLevel;
    private double currentValue;
    private UUID portfolioId;
    private String investmentType; // "Crypto", "Etf", "Savings", "Stock", "Other"
}
