// src/main/java/com/InvestmentsTracker/investment_portfolio/repository/OtherRepository.java
package com.InvestmentsTracker.investment_portfolio.repository;

import com.InvestmentsTracker.investment_portfolio.model.Other;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositório para operações CRUD na entidade Other.
 */
@Repository
public interface OtherRepository extends JpaRepository<Other, UUID> {

    /**
     * Busca um investimento "Other" pela descrição.
     *
     * @param description Descrição do investimento.
     * @return Optional contendo o investimento encontrado ou vazio caso contrário.
     */
    Optional<Other> findByDescription(String description);

    /**
     * Busca todos os investimentos "Other" associados a um portfólio específico.
     *
     * @param portfolioId ID do portfólio.
     * @return Lista de investimentos "Other".
     */
    List<Other> findByPortfolioId(UUID portfolioId);

    /**
     * Busca todos os investimentos "Other" associados a um usuário específico através do userId.
     *
     * @param userId ID do usuário.
     * @return Lista de investimentos "Other".
     */
    List<Other> findByPortfolioUserId(UUID userId);
}
