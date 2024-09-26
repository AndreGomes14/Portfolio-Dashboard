package com.InvestmentsTracker.investment_portfolio.service;

import com.InvestmentsTracker.investment_portfolio.model.Investment;

import java.util.List;

/**
 * Interface para serviços de domínio que gerenciam operações gerais de Investimentos.
 */
public interface InvestmentService {

    /**
     * Adiciona um novo investimento.
     *
     * @param investment Instância de Investment a ser adicionada.
     * @return Investment criado.
     */
    Investment addInvestment(Investment investment);

    /**
     * Atualiza um investimento existente.
     *
     * @param investment Instância de Investment a ser atualizada.
     * @return Investment atualizado.
     */
    Investment updateInvestment(Investment investment);

    /**
     * Remove um investimento específico.
     *
     * @param investmentId ID do investimento a ser removido.
     */
    void removeInvestment(Long investmentId);

    /**
     * Recupera um investimento por ID.
     *
     * @param investmentId ID do investimento.
     * @return Investment encontrado.
     */
    Investment getInvestmentById(Long investmentId);

    /**
     * Recupera todos os investimentos de um portfólio específico.
     *
     * @param portfolioId ID do portfólio.
     * @return Lista de investimentos.
     */
    List<Investment> getAllInvestmentsByPortfolio(Long portfolioId);

    /**
     * Recupera todos os investimentos de um usuário específico.
     *
     * @param userId ID do usuário.
     * @return Lista de investimentos.
     */
    List<Investment> getAllInvestmentsByUser(Long userId);

    List<Investment> getInvestmentsByPortfolioId(Long portfolioId);
}
