package com.InvestmentsTracker.investment_portfolio.repository;

import com.InvestmentsTracker.investment_portfolio.model.Investment;
import com.InvestmentsTracker.investment_portfolio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {
    List<Investment> findByUser(User user);

    /**
     * Busca todas as investimentas associadas a um portfólio específico.
     *
     * @param portfolioId ID do portfólio.
     * @return Lista de investimentos.
     */
    List<Investment> findByPortfolioId(Long portfolioId);

    /**
     * Busca todas as investimentas associadas a um usuário específico através do userId.
     *
     * @param userId ID do usuário.
     * @return Lista de investimentos.
     */
    List<Investment> findByPortfolioUserId(Long userId);
}
