package com.InvestmentsTracker.investment_portfolio.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PortfolioDTO {
    private Long id;
    private Long userId;
    private String name;
    private List<InvestmentDTO> investments;
    private double totalInvested;
    private double totalCurrentValue;
    private double totalProfitOrLoss;
}
