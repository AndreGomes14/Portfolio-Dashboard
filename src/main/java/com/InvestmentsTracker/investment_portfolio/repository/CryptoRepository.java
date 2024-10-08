package com.InvestmentsTracker.investment_portfolio.repository;

import com.InvestmentsTracker.investment_portfolio.model.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CryptoRepository extends JpaRepository<Crypto, UUID> {

    /**
     * Encontra criptomoedas pelo ID do portfólio.
     *
     * @param portfolioId ID do portfólio.
     * @return Lista de criptomoedas.
     */
    List<Crypto> findByPortfolioId(UUID portfolioId);

    /**
     * Encontra criptomoedas pelo ID do usuário.
     *
     * @param userId ID do usuário.
     * @return Lista de criptomoedas.
     */
    List<Crypto> findByPortfolioUserId(UUID userId);
}
