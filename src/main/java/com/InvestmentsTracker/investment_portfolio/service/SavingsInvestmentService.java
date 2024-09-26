package com.InvestmentsTracker.investment_portfolio.service;

import com.InvestmentsTracker.investment_portfolio.exception.DepositPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.model.Savings;

import java.util.List;

/**
 * Interface para serviços específicos de investimentos em Savings (Depósitos).
 */
public interface SavingsInvestmentService {

    /**
     * Atualiza manualmente o valor de um investimento em Savings específico.
     *
     * @param investmentId ID do investimento em Savings.
     * @param newValue Novo valor atual em EUR.
     * @throws DepositPriceRetrievalException Se ocorrer um erro ao atualizar o valor.
     */
    void updateValue(Long investmentId, double newValue) throws DepositPriceRetrievalException;

    /**
     * Atualiza os valores de todos os investimentos em Savings no portfólio do usuário.
     *
     * @param portfolioId ID do portfólio do usuário.
     * @param newValue Novo valor atual a ser definido para cada Savings.
     * @throws DepositPriceRetrievalException Se ocorrer um erro ao atualizar os valores.
     */
    void updateAllValues(Long portfolioId, double newValue) throws DepositPriceRetrievalException;

    /**
     * Calcula e retorna o valor atual de um investimento em Savings.
     *
     * @param investmentId ID do investimento em Savings.
     * @return Valor atual em EUR.
     * @throws DepositPriceRetrievalException Se ocorrer um erro ao recuperar o valor.
     */
    double getCurrentValue(Long investmentId) throws DepositPriceRetrievalException;

    /**
     * Recupera todas as instâncias de Savings associadas a um usuário específico.
     *
     * @param userId ID do usuário.
     * @return Lista de investimentos em Savings.
     */
    List<Savings> getAllDepositsByUser(Long userId);

    /**
     * Adiciona um novo investimento em Savings.
     *
     * @param savings Instância de Savings a ser adicionada.
     * @return Savings criado.
     */
    Savings addSavings(Savings savings);

    /**
     * Remove um investimento em Savings específico.
     *
     * @param investmentId ID do investimento em Savings.
     * @throws DepositPriceRetrievalException Se ocorrer um erro ao remover o Savings.
     */
    void removeSavings(Long investmentId) throws DepositPriceRetrievalException;
}
