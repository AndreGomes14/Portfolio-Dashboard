package com.InvestmentsTracker.investment_portfolio.repository;

import com.InvestmentsTracker.investment_portfolio.model.Portfolio;
import com.InvestmentsTracker.investment_portfolio.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    Optional<Portfolio> findByUserId(Long userId);
}