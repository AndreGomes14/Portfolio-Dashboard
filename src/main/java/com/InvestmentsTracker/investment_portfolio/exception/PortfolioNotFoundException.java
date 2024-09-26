package com.InvestmentsTracker.investment_portfolio.exception;

/**
 * Exceção lançada quando um portfólio não é encontrado para um usuário específico.
 */
public class PortfolioNotFoundException extends RuntimeException {

    public PortfolioNotFoundException() {
        super();
    }

    public PortfolioNotFoundException(String message) {
        super(message);
    }

    public PortfolioNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PortfolioNotFoundException(Throwable cause) {
        super(cause);
    }
}
