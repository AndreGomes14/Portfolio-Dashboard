// src/main/java/com/InvestmentsTracker/investment_portfolio/exception/EtfPriceRetrievalException.java
package com.InvestmentsTracker.investment_portfolio.exception;

/**
 * Exceção personalizada para erros ao recuperar ou atualizar preços de ETFs.
 */
public class EtfPriceRetrievalException extends Exception {

    public EtfPriceRetrievalException() {
        super();
    }

    public EtfPriceRetrievalException(String message) {
        super(message);
    }

    public EtfPriceRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }

    public EtfPriceRetrievalException(Throwable cause) {
        super(cause);
    }
}
