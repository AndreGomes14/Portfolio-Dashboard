package com.InvestmentsTracker.investment_portfolio.service;

import com.InvestmentsTracker.investment_portfolio.dto.PortfolioDTO;

public interface PortfolioService {
    PortfolioDTO getPortfolioByUserId(Long userId);
    PortfolioDTO createPortfolio(Long userId, PortfolioDTO portfolioDTO);

    // Additional methods as needed
}
