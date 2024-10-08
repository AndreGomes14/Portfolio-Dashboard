// src/main/java/com/InvestmentsTracker/investment_portfolio/service/crypto/CryptoInvestmentService.java

package com.InvestmentsTracker.investment_portfolio.service.crypto;

import com.InvestmentsTracker.investment_portfolio.exception.CryptoPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.model.Crypto;

import java.util.List;
import java.util.UUID;

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
    double updatePrice(UUID investmentId) throws CryptoPriceRetrievalException;

    /**
     * Atualiza os preços de todas as criptomoedas no portfólio do usuário.
     *
     * @param portfolioId ID do portfólio do usuário.
     * @throws CryptoPriceRetrievalException Se ocorrer um erro ao recuperar o preço.
     */
    void updateAllPrices(UUID portfolioId) throws CryptoPriceRetrievalException;

    /**
     * Calcula o valor atual de uma criptomoeda no investimento.
     *
     * @param investmentId ID do investimento em criptomoeda.
     * @return Valor atual em EUR.
     * @throws CryptoPriceRetrievalException Se ocorrer um erro ao recuperar o preço.
     */
    double getCurrentValue(UUID investmentId) throws CryptoPriceRetrievalException;

    /**
     * Recupera todas as criptomoedas associadas a um usuário.
     *
     * @param userId ID do usuário.
     * @return Lista de criptomoedas.
     */
    List<Crypto> getAllCryptosByUser(UUID userId);

    /**
     * Adiciona uma nova criptomoeda ao portfólio.
     *
     * @param cryptoRequestDTO DTO contendo os dados da criptomoeda.
     * @return Criptomoeda adicionada.
     */
    Crypto addCrypto(Crypto cryptoRequestDTO) throws CryptoPriceRetrievalException;

    /**
     * Remove uma criptomoeda específica do portfólio.
     *
     * @param cryptoId ID da criptomoeda a ser removida.
     * @throws CryptoPriceRetrievalException Se ocorrer um erro ao remover a criptomoeda.
     */
    void removeCrypto(UUID cryptoId) throws CryptoPriceRetrievalException;
}
