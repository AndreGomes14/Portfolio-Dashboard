package com.InvestmentsTracker.investment_portfolio.service.etf;

import com.InvestmentsTracker.investment_portfolio.exception.EtfPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.model.Etf;

import java.util.List;

/**
 * Interface para serviços específicos de investimentos em ETFs.
 */
public interface EtfInvestmentService {

    /**
     * Atualiza manualmente o valor de um investimento em ETF específico.
     *
     * @param investmentId ID do investimento em ETF.
     * @param newValue Novo valor atual em EUR.
     * @throws EtfPriceRetrievalException Se ocorrer um erro ao atualizar o valor.
     */
    void updateValue(Long investmentId, double newValue) throws EtfPriceRetrievalException;

    /**
     * Atualiza os valores de todos os investimentos em ETFs no portfólio do usuário.
     *
     * @param portfolioId ID do portfólio do usuário.
     * @param newValue Novo valor atual a ser definido para cada ETF.
     * @throws EtfPriceRetrievalException Se ocorrer um erro ao atualizar os valores.
     */
    void updateAllValues(Long portfolioId, double newValue) throws EtfPriceRetrievalException;

    /**
     * Recupera o valor atual de um investimento em ETF.
     *
     * @param investmentId ID do investimento em ETF.
     * @return Valor atual em EUR.
     * @throws EtfPriceRetrievalException Se ocorrer um erro ao recuperar o valor.
     */
    double getCurrentValue(Long investmentId) throws EtfPriceRetrievalException;

    /**
     * Recupera todas as instâncias de ETFs associadas a um usuário específico.
     *
     * @param userId ID do usuário.
     * @return Lista de investimentos em ETFs.
     */
    List<Etf> getAllEtfsByUser(Long userId);

    /**
     * Adiciona um novo investimento em ETF.
     *
     * @param etf Instância de Etf a ser adicionada.
     * @return Etf criado.
     */
    Etf addEtf(Etf etf);

    /**
     * Remove um investimento em ETF específico.
     *
     * @param investmentId ID do investimento em ETF.
     * @throws EtfPriceRetrievalException Se ocorrer um erro ao remover o ETF.
     */
    void removeEtf(Long investmentId) throws EtfPriceRetrievalException;
}
