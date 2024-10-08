package com.InvestmentsTracker.investment_portfolio.dto.etf;

import com.InvestmentsTracker.investment_portfolio.dto.investment.InvestmentResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class EtfResponseDTO extends InvestmentResponseDTO {

    private String ticker;
    private double units;
    private double lastSyncedPrice;
}
