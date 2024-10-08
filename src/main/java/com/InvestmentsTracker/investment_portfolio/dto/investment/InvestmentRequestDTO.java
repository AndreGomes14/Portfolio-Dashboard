package com.InvestmentsTracker.investment_portfolio.dto.investment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public abstract class InvestmentRequestDTO {

    @NotNull(message = "Buy price não pode ser nulo.")
    @Positive(message = "Buy price deve ser positivo.")
    private Double buyPrice;

    @NotNull(message = "Data não pode ser nula.")
    private String date; // Formato: "yyyy-MM-dd"

    @NotNull(message = "Risk level não pode ser nulo.")
    private Integer riskLevel;

    @NotNull(message = "Portfolio ID não pode ser nulo.")
    private UUID portfolioId;

    @NotNull(message = "Investment type não pode ser nulo.")
    private String investmentType; // Valores permitidos: "Crypto", "Etf", "Savings", "Stock", "Other"
}
