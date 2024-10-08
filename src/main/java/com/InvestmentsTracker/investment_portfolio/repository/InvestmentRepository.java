package com.InvestmentsTracker.investment_portfolio.repository;

import com.InvestmentsTracker.investment_portfolio.model.Investment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, UUID> {

    /**
     * Encontra investimentos pelo ID do portfólio.
     *
     * @param portfolioId ID do portfólio.
     * @return Lista de investimentos.
     */
    List<Investment> findByPortfolioId(UUID portfolioId);

    /**
     * Encontra investimentos pelo ID do usuário.
     *
     * @param userId ID do usuário.
     * @return Lista de investimentos.
     */
    List<Investment> findByPortfolioUserId(UUID userId);
}
