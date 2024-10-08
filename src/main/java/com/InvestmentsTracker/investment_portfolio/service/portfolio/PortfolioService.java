package com.InvestmentsTracker.investment_portfolio.service.portfolio;

import com.InvestmentsTracker.investment_portfolio.model.Portfolio;

import java.util.UUID;

public interface PortfolioService {

    /**
     * Verifica se um portfólio existe pelo seu ID.
     *
     * @param portfolioId ID do portfólio.
     * @return True se existir, false caso contrário.
     */
    boolean existsById(UUID portfolioId);

    /**
     * Recupera um portfólio pelo seu ID.
     *
     * @param portfolioId ID do portfólio.
     * @return Portfólio encontrado.
     */
    Portfolio getPortfolioById(UUID portfolioId);
}
