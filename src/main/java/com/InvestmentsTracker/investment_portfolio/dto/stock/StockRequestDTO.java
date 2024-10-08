package com.InvestmentsTracker.investment_portfolio.dto.stock;

import com.InvestmentsTracker.investment_portfolio.dto.investment.InvestmentRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockRequestDTO extends InvestmentRequestDTO {

    @NotBlank(message = "Ticker não pode ser vazio.")
    private String ticker;

    @NotNull(message = "Units não pode ser nulo.")
    @Positive(message = "Units deve ser positivo.")
    private Double units;
}
