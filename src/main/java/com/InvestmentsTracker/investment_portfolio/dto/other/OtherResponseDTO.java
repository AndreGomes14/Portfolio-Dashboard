package com.InvestmentsTracker.investment_portfolio.dto.other;

import com.InvestmentsTracker.investment_portfolio.dto.investment.InvestmentResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtherResponseDTO extends InvestmentResponseDTO {

    private String description;
    private String category;
}