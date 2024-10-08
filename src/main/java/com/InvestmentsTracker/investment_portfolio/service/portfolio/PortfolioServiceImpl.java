package com.InvestmentsTracker.investment_portfolio.service.portfolio;

import com.InvestmentsTracker.investment_portfolio.exception.PortfolioNotFoundException;
import com.InvestmentsTracker.investment_portfolio.model.Portfolio;
import com.InvestmentsTracker.investment_portfolio.repository.PortfolioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;

    @Autowired
    public PortfolioServiceImpl(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    @Override
    public boolean existsById(UUID portfolioId) {
        return portfolioRepository.existsById(portfolioId);
    }

    @Override
    public Portfolio getPortfolioById(UUID portfolioId) {
        return portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new PortfolioNotFoundException("Portfólio não encontrado com ID: " + portfolioId));
    }
}
