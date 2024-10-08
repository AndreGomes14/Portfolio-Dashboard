package com.InvestmentsTracker.investment_portfolio.service.etf;

import com.InvestmentsTracker.investment_portfolio.exception.EtfPriceRetrievalException;

/**
 * Interface para serviços que recuperam preços de Etfs.
 */
public interface EtfPriceService {

    /**
     * Obtém o preço atual de uma Etf em EUR.
     *
     * @param ticker Símbolo da Etf (ex: "SPY", "IVV")
     * @return Preço da Etf em EUR.
     * @throws EtfPriceRetrievalException Se ocorrer um erro ao recuperar o preço.
     */
    double getEtfPriceInEUR(String ticker) throws EtfPriceRetrievalException;
}
