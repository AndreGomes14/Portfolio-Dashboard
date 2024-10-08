package com.InvestmentsTracker.investment_portfolio.exception;

/**
 * Exceção personalizada para erros na recuperação de preços de Etfs.
 */
public class EtfPriceRetrievalException extends InvestmentException {

    public EtfPriceRetrievalException(String message) {
        super(message);
    }

    public EtfPriceRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }
}
