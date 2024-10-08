package com.InvestmentsTracker.investment_portfolio.dto.other;

import com.InvestmentsTracker.investment_portfolio.dto.investment.InvestmentRequestDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtherRequestDTO extends InvestmentRequestDTO {

    @NotBlank(message = "Descrição não pode ser vazia.")
    private String description;

    @NotBlank(message = "Categoria não pode ser vazia.")
    private String category;
}
