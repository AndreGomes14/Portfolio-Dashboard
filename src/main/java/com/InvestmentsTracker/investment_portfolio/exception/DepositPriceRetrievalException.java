package com.InvestmentsTracker.investment_portfolio.exception;

/**
 * Exceção personalizada para erros ao recuperar ou calcular preços de Savings.
 */
public class DepositPriceRetrievalException extends Exception {
    public DepositPriceRetrievalException() {
        super();
    }

    public DepositPriceRetrievalException(String message) {
        super(message);
    }

    public DepositPriceRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }

    public DepositPriceRetrievalException(Throwable cause) {
        super(cause);
    }
}
