package com.InvestmentsTracker.investment_portfolio.exception;

/**
 * Exceção personalizada para erros relacionados a investimentos.
 */
public class InvestmentException extends RuntimeException {

    public InvestmentException(String message) {
        super(message);
    }

    public InvestmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
