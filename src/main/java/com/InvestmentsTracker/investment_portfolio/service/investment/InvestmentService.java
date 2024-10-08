package com.InvestmentsTracker.investment_portfolio.service.investment;

import com.InvestmentsTracker.investment_portfolio.dto.investment.InvestmentRequestDTO;
import com.InvestmentsTracker.investment_portfolio.exception.InvestmentException;
import com.InvestmentsTracker.investment_portfolio.model.Investment;

import java.util.List;
import java.util.UUID;

public interface InvestmentService {

    /**
     * Atualiza o preço de um investimento específico.
     *
     * @param investmentId ID do investimento.
     * @return Preço atualizado.
     * @throws InvestmentException Se ocorrer um erro ao atualizar o preço.
     */
    double updatePrice(UUID investmentId) throws InvestmentException;

    /**
     * Atualiza os preços de todos os investimentos no portfólio do usuário.
     *
     * @param portfolioId ID do portfólio.
     * @throws InvestmentException Se ocorrer um erro ao atualizar os preços.
     */
    void updateAllPrices(UUID portfolioId) throws InvestmentException;

    /**
     * Calcula o valor atual de um investimento.
     *
     * @param investmentId ID do investimento.
     * @return Valor atual.
     * @throws InvestmentException Se ocorrer um erro ao calcular o valor.
     */
    double getCurrentValue(UUID investmentId) throws InvestmentException;

    /**
     * Recupera todos os investimentos associados a um usuário.
     *
     * @param userId ID do usuário.
     * @return Lista de investimentos.
     */
    List<Investment> getAllInvestmentsByUser(UUID userId);

    /**
     * Adiciona um novo investimento.
     *
     * @param investmentRequestDTO DTO contendo os dados do investimento.
     * @return Investimento adicionado.
     * @throws InvestmentException Se ocorrer um erro ao adicionar o investimento.
     */
    Investment addInvestment(InvestmentRequestDTO investmentRequestDTO) throws InvestmentException;

    /**
     * Remove um investimento específico.
     *
     * @param investmentId ID do investimento a ser removido.
     * @throws InvestmentException Se ocorrer um erro ao remover o investimento.
     */
    void removeInvestment(UUID investmentId) throws InvestmentException;
}
