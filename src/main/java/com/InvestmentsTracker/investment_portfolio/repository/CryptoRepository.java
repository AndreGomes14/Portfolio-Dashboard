// src/main/java/com/InvestmentsTracker/investment_portfolio/repository/CryptoRepository.java
package com.InvestmentsTracker.investment_portfolio.repository;

import com.InvestmentsTracker.investment_portfolio.model.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações CRUD na entidade Crypto.
 */
@Repository
public interface CryptoRepository extends JpaRepository<Crypto, Long> {

    /**
     * Busca uma criptomoeda pelo seu coinId.
     *
     * @param coinId ID da criptomoeda (ex: "bitcoin", "ethereum")
     * @return Optional contendo a Crypto encontrada ou vazia caso contrário.
     */
    Optional<Crypto> findByCoinId(String coinId);

    /**
     * Busca todas as criptomoedas associadas a um usuário através do portfolioId.
     *
     * @param portfolioId ID do portfólio do usuário.
     * @return Lista de criptomoedas.
     */
    List<Crypto> findByPortfolioId(Long portfolioId);

    /**
     * Busca todas as criptomoedas associadas a um usuário através do userId.
     *
     * @param userId ID do usuário.
     * @return Lista de criptomoedas.
     */
    List<Crypto> findByPortfolioUserId(Long userId);
}
