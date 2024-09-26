package com.InvestmentsTracker.investment_portfolio.service;

import com.InvestmentsTracker.investment_portfolio.dto.*;
import com.InvestmentsTracker.investment_portfolio.exception.InvalidInputException;
import com.InvestmentsTracker.investment_portfolio.exception.ResourceNotFoundException;
import com.InvestmentsTracker.investment_portfolio.model.*;
import com.InvestmentsTracker.investment_portfolio.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private InvestmentRepository investmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InvestmentService investmentService;

    @Override
    public PortfolioDTO getPortfolioByUserId(Long userId) {
        // Buscar o portfolio associado ao usuário pelo `userId`.
        Portfolio portfolio = portfolioRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("Portfolio not found for user ID: " + userId));

        // Converter a entidade `Portfolio` para um `PortfolioDTO`.
        PortfolioDTO dto = new PortfolioDTO();
        dto.setId(portfolio.getId()); // Usar o ID do portfolio, não do usuário
        dto.setUserId(userId);
        dto.setName(portfolio.getName()); // Assumindo que `Portfolio` tem um `name`.

        // Buscar todos os investimentos associados ao `Portfolio`.
        List<Investment> investments = investmentService.getInvestmentsByPortfolioId(portfolio.getId());

        // Converter a lista de `Investments` para uma lista de `InvestmentDTO` para o DTO de Portfolio.
        List<InvestmentDTO> investmentDTOs = investments.stream()
                .map(investment -> {
                    // Converter cada `Investment` para `InvestmentDTO`
                    InvestmentDTO investmentDTO = new InvestmentDTO();
                    investmentDTO.setId(investment.getId());
                    investmentDTO.setPurchasePrice(investment.getBuyPrice());
                    investmentDTO.setQuantity(investment.getUnits());
                    investmentDTO.setCurrentValue(investment.getCurrentMarketPrice());
                    investmentDTO.setName(investment.getInvestmentType());
                    // Adicionar mais campos conforme necessário
                    return investmentDTO;
                })
                .collect(Collectors.toList());

        dto.setInvestments(investmentDTOs);

        // Calcular o total investido e o valor atual.
        double totalInvested = investments.stream()
                .mapToDouble(Investment::getAmountInvested)
                .sum();

        double totalCurrentValue = investments.stream()
                .mapToDouble(Investment::getCurrentValue)
                .sum();

        dto.setTotalInvested(totalInvested);
        dto.setTotalCurrentValue(totalCurrentValue);
        dto.setTotalProfitOrLoss(totalCurrentValue - totalInvested);

        return dto;
    }


    @Override
    @Transactional
    public PortfolioDTO createPortfolio(Long userId, PortfolioDTO portfolioDTO) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            if (portfolioDTO.getName() == null || portfolioDTO.getName().isEmpty()) {
                throw new InvalidInputException("Portfolio name cannot be empty");
            }

            Portfolio portfolio = new Portfolio();
            portfolio.setName(portfolioDTO.getName());
            portfolio.setUser(user);

            Portfolio savedPortfolio = portfolioRepository.save(portfolio);
            return convertToDTO(savedPortfolio);
        } catch (Exception e) {
            // Log the exception if necessary
            throw e; // Re-throw the exception to be handled by the global exception handler
        }
    }

    private PortfolioDTO convertToDTO(Portfolio portfolio) {
        PortfolioDTO dto = new PortfolioDTO();
        dto.setId(portfolio.getId());
        dto.setName(portfolio.getName());
        dto.setUserId(portfolio.getUser().getId());
        return dto;
    }
}
