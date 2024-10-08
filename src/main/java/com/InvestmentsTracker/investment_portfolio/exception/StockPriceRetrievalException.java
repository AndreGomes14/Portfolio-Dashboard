// src/main/java/com/InvestmentsTracker/investment_portfolio/exception/StockPriceRetrievalException.java
package com.InvestmentsTracker.investment_portfolio.exception;

/**
 * Exceção personalizada para erros ao recuperar preços de ações.
 */
public class StockPriceRetrievalException extends InvestmentException {
    /**
     * Construtor com mensagem.
     *
     * @param message Mensagem detalhando o erro.
     */
    public StockPriceRetrievalException(String message) {
        super(message);
    }

    /**
     * Construtor com mensagem e causa.
     *
     * @param message Mensagem detalhando o erro.
     * @param cause   Causa subjacente do erro.
     */
    public StockPriceRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Construtor com causa.
     *
     * @param cause Causa subjacente do erro.
     */
}
