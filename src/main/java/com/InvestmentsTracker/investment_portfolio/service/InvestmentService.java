package com.InvestmentsTracker.investment_portfolio.service;

import com.InvestmentsTracker.investment_portfolio.dto.InvestmentDTO;
import java.util.List;

public interface InvestmentService {
    List<InvestmentDTO> getInvestmentsByPortfolioId(Long portfolioId);
    InvestmentDTO getInvestmentById(Long investmentId);
    InvestmentDTO createInvestment(Long portfolioId, InvestmentDTO investmentDTO);
    InvestmentDTO updateInvestment(Long investmentId, InvestmentDTO investmentDTO);
    void deleteInvestment(Long investmentId);
}
