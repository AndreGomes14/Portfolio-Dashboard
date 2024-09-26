package com.InvestmentsTracker.investment_portfolio.repository;

import com.InvestmentsTracker.investment_portfolio.model.Etf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações CRUD na entidade Etf.
 */
@Repository
public interface EtfRepository extends JpaRepository<Etf, Long> {

    /**
     * Busca um ETF pelo nome do fundo.
     *
     * @param fundName Nome do fundo.
     * @return Optional contendo o ETF encontrado ou vazio caso contrário.
     */
    Optional<Etf> findByFundName(String fundName);

    /**
     * Busca todos os ETFs associados a um portfólio específico.
     *
     * @param portfolioId ID do portfólio.
     * @return Lista de ETFs.
     */
    List<Etf> findByPortfolioId(Long portfolioId);

    /**
     * Busca todos os ETFs associados a um usuário específico através do userId.
     *
     * @param userId ID do usuário.
     * @return Lista de ETFs.
     */
    List<Etf> findByPortfolioUserId(Long userId);
}
