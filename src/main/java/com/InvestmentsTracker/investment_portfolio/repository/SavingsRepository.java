// src/main/java/com/InvestmentsTracker/investment_portfolio/repository/SavingsRepository.java
package com.InvestmentsTracker.investment_portfolio.repository;

import com.InvestmentsTracker.investment_portfolio.model.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repositório para operações CRUD na entidade Savings.
 */
@Repository
public interface SavingsRepository extends JpaRepository<Savings, UUID> {

    /**
     * Busca todas as Savings associadas a um portfólio específico.
     *
     * @param portfolioId ID do portfólio.
     * @return Lista de Savings.
     */
    List<Savings> findByPortfolioId(UUID portfolioId);

    /**
     * Busca todas as Savings associadas a um usuário específico através do userId.
     *
     * @param userId ID do usuário.
     * @return Lista de Savings.
     */
    List<Savings> findByPortfolioUserId(UUID userId);
}
