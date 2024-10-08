package com.InvestmentsTracker.investment_portfolio.repository;

import com.InvestmentsTracker.investment_portfolio.model.Portfolio;
import com.InvestmentsTracker.investment_portfolio.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PortfolioRepository extends JpaRepository<Portfolio, UUID> {
    Optional<Portfolio> findByUserId(UUID userId);
}