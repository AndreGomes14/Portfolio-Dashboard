package com.InvestmentsTracker.investment_portfolio.service.investment;

import com.InvestmentsTracker.investment_portfolio.model.Investment;
import com.InvestmentsTracker.investment_portfolio.repository.InvestmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementação do InvestmentService que gerencia operações gerais de Investimentos.
 */
@Service
@Slf4j
public class InvestmentServiceImpl implements InvestmentService {

    private final InvestmentRepository investmentRepository;

    @Autowired
    public InvestmentServiceImpl(InvestmentRepository investmentRepository) {
        this.investmentRepository = investmentRepository;
    }

    @Override
    public Investment addInvestment(Investment investment) {
        Investment savedInvestment = investmentRepository.save(investment);
        log.info("Novo investimento adicionado: {}", savedInvestment);
        return savedInvestment;
    }

    @Override
    public Investment updateInvestment(Investment investment) {
        if (!investmentRepository.existsById(investment.getId())) {
            log.warn("Tentativa de atualizar investimento inexistente: ID {}", investment.getId());
            throw new IllegalArgumentException("Investimento não encontrado com ID: " + investment.getId());
        }
        Investment updatedInvestment = investmentRepository.save(investment);
        log.info("Investimento atualizado: {}", updatedInvestment);
        return updatedInvestment;
    }

    @Override
    public void removeInvestment(Long investmentId) {
        if (!investmentRepository.existsById(investmentId)) {
            log.warn("Tentativa de remover investimento inexistente: ID {}", investmentId);
            throw new IllegalArgumentException("Investimento não encontrado com ID: " + investmentId);
        }
        investmentRepository.deleteById(investmentId);
        log.info("Investimento removido: ID {}", investmentId);
    }

    @Override
    public Investment getInvestmentById(Long investmentId) {
        Optional<Investment> investmentOpt = investmentRepository.findById(investmentId);
        if (investmentOpt.isPresent()) {
            log.info("Investimento recuperado: {}", investmentOpt.get());
            return investmentOpt.get();
        } else {
            log.warn("Investimento não encontrado: ID {}", investmentId);
            throw new IllegalArgumentException("Investimento não encontrado com ID: " + investmentId);
        }
    }

    @Override
    public List<Investment> getAllInvestmentsByPortfolio(Long portfolioId) {
        List<Investment> investments = investmentRepository.findByPortfolioId(portfolioId);
        log.info("Recuperados {} investimentos para o portfólio ID {}", investments.size(), portfolioId);
        return investments;
    }

    @Override
    public List<Investment> getAllInvestmentsByUser(Long userId) {
        List<Investment> investments = investmentRepository.findByPortfolioUserId(userId);
        log.info("Recuperados {} investimentos para o usuário ID {}", investments.size(), userId);
        return investments;
    }
}
