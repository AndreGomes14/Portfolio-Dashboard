package com.InvestmentsTracker.investment_portfolio.service;

import com.InvestmentsTracker.investment_portfolio.dto.InvestmentDTO;
import com.InvestmentsTracker.investment_portfolio.model.*;
import com.InvestmentsTracker.investment_portfolio.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class InvestmentServiceImpl implements InvestmentService {

    @Autowired
    private InvestmentRepository investmentRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Override
    public List<InvestmentDTO> getInvestmentsByPortfolioId(Long portfolioId) {
        List<Investment> investments = investmentRepository.findByPortfolioId(portfolioId);
        return convertToDTOList(investments);
    }

    @Override
    public InvestmentDTO getInvestmentById(Long investmentId) {
        Investment investment = investmentRepository.findById(investmentId)
                .orElseThrow(() -> new NoSuchElementException("Investment not found"));
        return convertToDTO(investment);
    }

    @Override
    @Transactional
    public InvestmentDTO createInvestment(Long portfolioId, InvestmentDTO investmentDTO) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new NoSuchElementException("Portfolio not found"));

        Investment investment = convertToEntity(investmentDTO);
        investment.setPortfolio(portfolio);

        Investment savedInvestment = investmentRepository.save(investment);
        return convertToDTO(savedInvestment);
    }

    @Override
    @Transactional
    public InvestmentDTO updateInvestment(Long investmentId, InvestmentDTO investmentDTO) {
        Investment existingInvestment = investmentRepository.findById(investmentId)
                .orElseThrow(() -> new NoSuchElementException("Investment not found"));

        updateEntityFromDTO(existingInvestment, investmentDTO);
        Investment updatedInvestment = investmentRepository.save(existingInvestment);
        return convertToDTO(updatedInvestment);
    }

    @Override
    @Transactional
    public void deleteInvestment(Long investmentId) {
        if (!investmentRepository.existsById(investmentId)) {
            throw new NoSuchElementException("Investment not found");
        }
        investmentRepository.deleteById(investmentId);
    }

    // Helper methods to convert between entity and DTO
    private InvestmentDTO convertToDTO(Investment investment) {
        InvestmentDTO dto = new InvestmentDTO();
        dto.setId(investment.getId());
        dto.setName(investment.getName());
        dto.setType(investment.getClass().getSimpleName());
        dto.setAmountInvested(investment.getAmountInvested());
        dto.setCurrentValue(investment.getCurrentValue());

        double profitOrLoss = investment.getCurrentValue() - investment.getAmountInvested();
        dto.setProfitOrLoss(profitOrLoss);

        return dto;
    }

    private List<InvestmentDTO> convertToDTOList(List<Investment> investments) {
        List<InvestmentDTO> dtos = new ArrayList<>();
        for (Investment investment : investments) {
            dtos.add(convertToDTO(investment));
        }
        return dtos;
    }

    private Investment convertToEntity(InvestmentDTO dto) {
        Investment investment;
        switch (dto.getType()) {
            case "Crypto":
                investment = new Crypto();
                break;
            case "Stock":
                investment = new Stock();
                break;
            case "Etf":
                investment = new Etf();
                break;
            case "SavingsAndDeposits":
                investment = new SavingsAndDeposits();
                break;
            case "Other":
                investment = new Other();
                break;
            default:
                throw new IllegalArgumentException("Invalid investment type");
        }
        updateEntityFromDTO(investment, dto);
        return investment;
    }

    private void updateEntityFromDTO(Investment investment, InvestmentDTO dto) {
        investment.setName(dto.getName());
        investment.setAmountInvested(dto.getAmountInvested());
        investment.setCurrentValue(dto.getCurrentValue());
        // Update other fields as necessary
    }
}
