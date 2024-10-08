package com.InvestmentsTracker.investment_portfolio.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe para representar detalhes de erros nas respostas da API.
 */
@Getter
@Setter
@AllArgsConstructor
public class ErrorDetails {
    private String message;
    private String details;
}
