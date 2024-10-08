package com.InvestmentsTracker.investment_portfolio.dto.savings;

import com.InvestmentsTracker.investment_portfolio.dto.investment.InvestmentRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SavingsRequestDTO extends InvestmentRequestDTO {

    @NotBlank(message = "Nome da conta não pode ser vazio.")
    private String accountName;

    @NotNull(message = "Taxa de juros não pode ser nula.")
    @Positive(message = "Taxa de juros deve ser positiva.")
    private Double interestRate;
}
