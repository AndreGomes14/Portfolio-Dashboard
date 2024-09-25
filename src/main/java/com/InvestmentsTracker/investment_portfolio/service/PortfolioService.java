package com.InvestmentsTracker.investment_portfolio.service;

import com.InvestmentsTracker.investment_portfolio.dto.PortfolioDTO;

public interface PortfolioService {
    PortfolioDTO getPortfolioByUserId(Long userId);
    // Additional methods as needed
}
