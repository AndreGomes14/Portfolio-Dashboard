package com.InvestmentsTracker.investment_portfolio.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class InvestmentDTO {
    private Long id;
    private String type; // e.g., "Crypto", "Stock", etc.
    private String name; // Name of the investment
    private double amountInvested;
    private double currentValue;
    private double profitOrLoss;
    // Add other common fields as needed
}
