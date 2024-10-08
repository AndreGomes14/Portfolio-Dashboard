// src/main/java/com/InvestmentsTracker/investment_portfolio/repository/StockRepository.java
package com.InvestmentsTracker.investment_portfolio.repository;

import com.InvestmentsTracker.investment_portfolio.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositório para operações CRUD na entidade Stock.
 */
@Repository
public interface StockRepository extends JpaRepository<Stock, UUID> {

    /**
     * Busca um Stock pelo ticker.
     *
     * @param ticker Ticker da ação.
     * @return Optional contendo o Stock encontrado ou vazio caso contrário.
     */
    Optional<Stock> findByTicker(String ticker);

    /**
     * Busca todos os Stocks associados a um portfólio específico.
     *
     * @param portfolioId ID do portfólio.
     * @return Lista de Stocks.
     */
    List<Stock> findByPortfolioId(UUID portfolioId);

    /**
     * Busca todos os Stocks associados a um usuário específico através do userId.
     *
     * @param userId ID do usuário.
     * @return Lista de Stocks.
     */
    List<Stock> findByPortfolioUserId(UUID userId);
}
