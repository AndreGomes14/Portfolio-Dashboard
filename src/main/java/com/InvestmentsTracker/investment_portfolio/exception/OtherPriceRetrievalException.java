// src/main/java/com/InvestmentsTracker/investment_portfolio/exception/OtherPriceRetrievalException.java
package com.InvestmentsTracker.investment_portfolio.exception;

/**
 * Exceção personalizada para erros ao recuperar ou atualizar valores de investimentos "Other".
 */
public class OtherPriceRetrievalException extends InvestmentException {

    public OtherPriceRetrievalException(String message) {
        super(message);
    }

    public OtherPriceRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }

}
