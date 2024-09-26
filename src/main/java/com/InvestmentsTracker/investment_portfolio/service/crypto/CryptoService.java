// src/main/java/com/InvestmentsTracker/investment_portfolio/service/CryptoService.java
package com.InvestmentsTracker.investment_portfolio.service.crypto;

import com.InvestmentsTracker.investment_portfolio.exception.CryptoPriceRetrievalException;

/**
 * Interface para serviços que recuperam preços de criptomoedas.
 */
public interface CryptoService {

    /**
     * Obtém o preço atual de uma criptomoeda em EUR.
     *
     * @param ticker Símbolo da criptomoeda (ex: "BTC", "ETH")
     * @return Preço da criptomoeda em EUR.
     * @throws CryptoPriceRetrievalException Se ocorrer um erro ao recuperar o preço.
     */
    double getCryptoPriceInEUR(String ticker) throws CryptoPriceRetrievalException;
}
