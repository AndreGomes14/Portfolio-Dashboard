package com.InvestmentsTracker.investment_portfolio.service;

import com.InvestmentsTracker.investment_portfolio.exception.CryptoPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.model.Crypto;

import java.util.List;

/**
 * Interface para serviços específicos de investimentos em criptomoedas.
 */
public interface CryptoInvestmentService {

    /**
     * Atualiza o preço de uma criptomoeda específica no investimento.
     *
     * @param investmentId ID do investimento em criptomoeda.
     * @return Preço atualizado em EUR.
     * @throws CryptoPriceRetrievalException Se ocorrer um erro ao recuperar o preço.
     */
    double updatePrice(Long investmentId) throws CryptoPriceRetrievalException;

    /**
     * Atualiza os preços de todas as criptomoedas no portfólio do usuário.
     *
     * @param portfolioId ID do portfólio do usuário.
     * @throws CryptoPriceRetrievalException Se ocorrer um erro ao recuperar o preço.
     */
    void updateAllPrices(Long portfolioId) throws CryptoPriceRetrievalException;

    /**
     * Calcula o valor atual de uma criptomoeda no investimento.
     *
     * @param investmentId ID do investimento em criptomoeda.
     * @return Valor atual em EUR.
     * @throws CryptoPriceRetrievalException Se ocorrer um erro ao recuperar o preço.
     */
    double getCurrentValue(Long investmentId) throws CryptoPriceRetrievalException;

    /**
     * Recupera todas as criptomoedas associadas a um usuário.
     *
     * @param userId ID do usuário.
     * @return Lista de criptomoedas.
     */
    List<Crypto> getAllCryptosByUser(Long userId);
}
