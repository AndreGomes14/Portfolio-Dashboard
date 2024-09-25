package com.InvestmentsTracker.investment_portfolio.service;

import com.InvestmentsTracker.investment_portfolio.dto.*;
import com.InvestmentsTracker.investment_portfolio.model.*;
import com.InvestmentsTracker.investment_portfolio.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private InvestmentRepository investmentRepository;

    @Autowired
    private InvestmentService investmentService;

    @Override
    public PortfolioDTO getPortfolioByUserId(Long userId) {
        Portfolio portfolio = (Portfolio) portfolioRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("Portfolio not found"));

        PortfolioDTO dto = new PortfolioDTO();
        dto.setId(portfolio.getUser().getId());
        dto.setUserId(userId);
        dto.setUsername(portfolio.getUser().getUsername());

        List<InvestmentDTO> investmentDTOs = investmentService.getInvestmentsByPortfolioId(portfolio.getUser().getId());
        dto.setInvestments(investmentDTOs);

        // Calculate totals
        double totalInvested = 0;
        double totalCurrentValue =0;

        for (InvestmentDTO investmentDTO : investmentDTOs) {
            totalInvested+= investmentDTO.getAmountInvested();
            totalCurrentValue += investmentDTO.getCurrentValue();
        }

        dto.setTotalInvested(totalInvested);
        dto.setTotalCurrentValue(totalCurrentValue);
        dto.setTotalProfitOrLoss(totalCurrentValue - totalInvested);

        return dto;
    }
}
