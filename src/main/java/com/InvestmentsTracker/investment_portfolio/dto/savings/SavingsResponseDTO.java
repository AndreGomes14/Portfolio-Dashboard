package com.InvestmentsTracker.investment_portfolio.dto.savings;

import com.InvestmentsTracker.investment_portfolio.dto.investment.InvestmentResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SavingsResponseDTO extends InvestmentResponseDTO {

    private String accountName;
    private double interestRate;
}
