package com.InvestmentsTracker.investment_portfolio.service.etf;

import com.InvestmentsTracker.investment_portfolio.dto.etf.EtfRequestDTO;
import com.InvestmentsTracker.investment_portfolio.exception.EtfPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.exception.InvestmentException;
import com.InvestmentsTracker.investment_portfolio.model.Etf;

import java.util.List;
import java.util.UUID;

/**
 * Interface para serviços específicos de investimentos em ETFs.
 */
public interface EtfInvestmentService {

    /**
     * Atualiza o preço de um ETF específico no investimento.
     *
     * @param investmentId ID do investimento em ETF.
     * @return Preço atualizado em EUR.
     * @throws InvestmentException Se ocorrer um erro ao recuperar o preço.
     */
    double updatePrice(UUID investmentId) throws InvestmentException;

    /**
     * Atualiza os preços de todos os ETFs no portfólio do usuário.
     *
     * @param portfolioId ID do portfólio do usuário.
     * @throws EtfPriceRetrievalException Se ocorrer um erro ao recuperar o preço.
     */
    void updateAllPrices(UUID portfolioId) throws EtfPriceRetrievalException;

    /**
     * Calcula o valor atual de um ETF no investimento.
     *
     * @param investmentId ID do investimento em ETF.
     * @return Valor atual em EUR.
     * @throws InvestmentException Se ocorrer um erro ao recuperar o preço.
     */
    double getCurrentValue(UUID investmentId) throws InvestmentException;

    /**
     * Recupera todas as Etfs associadas a um usuário.
     *
     * @param userId ID do usuário.
     * @return Lista de Etfs.
     */
    List<Etf> getAllEtfsByUser(UUID userId);

    /**
     * Adiciona uma nova Etf ao portfólio.
     *
     * @param etfRequestDTO DTO contendo os dados da Etf.
     * @return Etf adicionada.
     * @throws EtfPriceRetrievalException Se ocorrer um erro ao adicionar a Etf.
     */
    Etf addEtf(EtfRequestDTO etfRequestDTO) throws EtfPriceRetrievalException;

    /**
     * Remove uma Etf específica do portfólio.
     *
     * @param etfId ID da Etf a ser removida.
     * @throws EtfPriceRetrievalException Se ocorrer um erro ao remover a Etf.
     */
    void removeEtf(UUID etfId) throws EtfPriceRetrievalException;
}
