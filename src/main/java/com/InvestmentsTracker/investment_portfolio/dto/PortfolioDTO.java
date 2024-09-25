package com.InvestmentsTracker.investment_portfolio.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class PortfolioDTO {
    private Long id;
    private Long userId;
    private String username;
    private List<InvestmentDTO> investments;
    private double totalInvested;
    private double totalCurrentValue;
    private double totalProfitOrLoss;
}
