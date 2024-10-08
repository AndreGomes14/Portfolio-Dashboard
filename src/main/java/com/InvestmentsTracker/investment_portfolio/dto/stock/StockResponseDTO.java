package com.InvestmentsTracker.investment_portfolio.dto.stock;

import com.InvestmentsTracker.investment_portfolio.dto.investment.InvestmentResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockResponseDTO extends InvestmentResponseDTO {

    private String ticker;
    private double units;
    private double lastSyncedPrice;
}