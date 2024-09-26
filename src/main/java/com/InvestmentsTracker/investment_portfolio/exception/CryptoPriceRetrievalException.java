package com.InvestmentsTracker.investment_portfolio.exception;

public class CryptoPriceRetrievalException extends RuntimeException {

    /**
     * Construtor padrão.
     */
    public CryptoPriceRetrievalException() {
        super();
    }

    /**
     * Construtor com mensagem.
     *
     * @param message Mensagem detalhando o erro.
     */
    public CryptoPriceRetrievalException(String message) {
        super(message);
    }

    /**
     * Construtor com mensagem e causa.
     *
     * @param message Mensagem detalhando o erro.
     * @param cause   Causa subjacente do erro.
     */
    public CryptoPriceRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Construtor com causa.
     *
     * @param cause Causa subjacente do erro.
     */
    public CryptoPriceRetrievalException(Throwable cause) {
        super(cause);
    }
}
