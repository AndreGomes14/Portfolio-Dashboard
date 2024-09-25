package com.InvestmentsTracker.investment_portfolio.repository;

import com.InvestmentsTracker.investment_portfolio.model.Investment;
import com.InvestmentsTracker.investment_portfolio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {
    List<Investment> findByUser(User user);

    List<Investment> findByPortfolioId(Long portfolioId);
}