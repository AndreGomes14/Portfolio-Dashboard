package com.InvestmentsTracker.investment_portfolio.repository;

import com.InvestmentsTracker.investment_portfolio.model.Etf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EtfRepository extends JpaRepository<Etf, UUID> {

    /**
     * Encontra Etfs pelo ID do portfólio.
     *
     * @param portfolioId ID do portfólio.
     * @return Lista de Etfs.
     */
    List<Etf> findByPortfolioId(UUID portfolioId);

    /**
     * Encontra Etfs pelo ID do usuário.
     *
     * @param userId ID do usuário.
     * @return Lista de Etfs.
     */
    List<Etf> findByPortfolioUserId(UUID userId);
}
