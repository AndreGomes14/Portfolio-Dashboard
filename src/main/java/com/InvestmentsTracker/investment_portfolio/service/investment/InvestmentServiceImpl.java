// src/main/java/com/InvestmentsTracker/investment_portfolio/service/investment/InvestmentServiceImpl.java

package com.InvestmentsTracker.investment_portfolio.service.investment;

import com.InvestmentsTracker.investment_portfolio.dto.investment.InvestmentRequestDTO;
import com.InvestmentsTracker.investment_portfolio.exception.InvestmentException;
import com.InvestmentsTracker.investment_portfolio.mapper.InvestmentMapper;
import com.InvestmentsTracker.investment_portfolio.model.*;
import com.InvestmentsTracker.investment_portfolio.repository.InvestmentRepository;
import com.InvestmentsTracker.investment_portfolio.service.crypto.CryptoInvestmentService;
import com.InvestmentsTracker.investment_portfolio.service.etf.EtfInvestmentService;
import com.InvestmentsTracker.investment_portfolio.service.other.OtherInvestmentService;
import com.InvestmentsTracker.investment_portfolio.service.portfolio.PortfolioService;
import com.InvestmentsTracker.investment_portfolio.service.savings.SavingsInvestmentService;
import com.InvestmentsTracker.investment_portfolio.service.stock.StockInvestmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class InvestmentServiceImpl implements InvestmentService {

    private final InvestmentRepository investmentRepository;
    private final PortfolioService portfolioService;
    private final InvestmentMapper investmentMapper;

    // Serviços específicos por tipo de investimento
    private final CryptoInvestmentService cryptoInvestmentService;
    private final EtfInvestmentService etfInvestmentService;
    private final SavingsInvestmentService savingsInvestmentService;
    private final StockInvestmentService stockInvestmentService;
    private final OtherInvestmentService otherInvestmentService;

    @Autowired
    public InvestmentServiceImpl(InvestmentRepository investmentRepository,
                                 PortfolioService portfolioService,
                                 InvestmentMapper investmentMapper,
                                 CryptoInvestmentService cryptoInvestmentService,
                                 EtfInvestmentService etfInvestmentService,
                                 SavingsInvestmentService savingsInvestmentService,
                                 StockInvestmentService stockInvestmentService,
                                 OtherInvestmentService otherInvestmentService) {
        this.investmentRepository = investmentRepository;
        this.portfolioService = portfolioService;
        this.investmentMapper = investmentMapper;
        this.cryptoInvestmentService = cryptoInvestmentService;
        this.etfInvestmentService = etfInvestmentService;
        this.savingsInvestmentService = savingsInvestmentService;
        this.stockInvestmentService = stockInvestmentService;
        this.otherInvestmentService = otherInvestmentService;
    }

    @Override
    public double updatePrice(UUID investmentId) throws InvestmentException {
        Investment investment = investmentRepository.findById(investmentId)
                .orElseThrow(() -> new InvestmentException("Investment não encontrado com ID: " + investmentId));

        try {
            return switch (investment.getInvestmentType()) {
                case "Crypto" -> cryptoInvestmentService.updatePrice(investmentId);
                case "Etf" -> etfInvestmentService.updatePrice(investmentId);
                case "Savings" -> {
                    log.warn("Atualização de preço para Savings não implementada.");
                    throw new InvestmentException("Atualização de preço para Savings não implementada.");
                    // Para Savings, talvez não seja necessário atualizar preço, ou implementar lógica específica
                }
                case "Stock" -> stockInvestmentService.updatePrice(investmentId);
                case "Other" -> {
                    log.warn("Atualização de preço para Other não implementada.");
                    throw new InvestmentException("Atualização de preço para Other não implementada.");
                    // Implementar lógica específica ou lançar exceção
                }
                default ->
                        throw new InvestmentException("Tipo de investimento inválido: " + investment.getInvestmentType());
            };
        } catch (Exception e) {
            log.error("Erro ao atualizar o preço para Investment ID: {}: {}", investmentId, e.getMessage());
            // Verifique se a exceção já é uma InvestmentException
            if (e instanceof InvestmentException) {
                throw (InvestmentException) e;
            } else {
                throw new InvestmentException("Erro ao atualizar o preço do investimento.", e);
            }
        }
    }

    @Override
    public void updateAllPrices(UUID portfolioId) throws InvestmentException {
        List<Investment> investments = investmentRepository.findByPortfolioId(portfolioId);
        for (Investment investment : investments) {
            try {
                updatePrice(investment.getId());
            } catch (InvestmentException e) {
                log.error("Erro ao atualizar preço para Investment ID: {}", investment.getId(), e);
                // Dependendo da necessidade, pode optar por continuar ou interromper a atualização
                throw e;
            }
        }
        log.info("Todos os preços dos investimentos no portfólio ID {} foram atualizados.", portfolioId);
    }

    @Override
    public double getCurrentValue(UUID investmentId) throws InvestmentException {
        Investment investment = investmentRepository.findById(investmentId)
                .orElseThrow(() -> new InvestmentException("Investment não encontrado com ID: " + investmentId));

        try {
            return switch (investment.getInvestmentType()) {
                case "Crypto" -> cryptoInvestmentService.getCurrentValue(investmentId);
                case "Etf" -> etfInvestmentService.getCurrentValue(investmentId);
                case "Savings" -> savingsInvestmentService.getCurrentValue(investmentId);
                case "Stock" -> stockInvestmentService.getCurrentValue(investmentId);
                case "Other" -> otherInvestmentService.getCurrentValue(investmentId);
                default ->
                        throw new InvestmentException("Tipo de investimento inválido: " + investment.getInvestmentType());
            };
        } catch (InvestmentException e) {
            // Re-lançar a InvestmentException sem alterações
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao calcular o valor atual para Investment ID: {}: {}", investmentId, e.getMessage());
            throw new InvestmentException("Erro inesperado ao calcular o valor atual do investimento.", e);
        }
    }

    @Override
    public List<Investment> getAllInvestmentsByUser(UUID userId) {
        List<Investment> investments = investmentRepository.findByPortfolioUserId(userId);
        log.info("Encontrados {} investimentos para o usuário ID: {}", investments.size(), userId);
        return investments;
    }

    @Override
    public Investment addInvestment(InvestmentRequestDTO investmentRequestDTO) throws InvestmentException {
        // Verifica se o portfólio existe
        UUID portfolioId = investmentRequestDTO.getPortfolioId();
        if (!portfolioService.existsById(portfolioId)) {
            log.error("Portfólio não encontrado com ID: {}", portfolioId);
            throw new InvestmentException("Portfólio não encontrado com ID: " + portfolioId);
        }

        // Mapeia DTO para entidade específica
        Investment investment = investmentMapper.toEntity(investmentRequestDTO);
        investment.setPortfolio(portfolioService.getPortfolioById(portfolioId));

        // Inicializa valores
        investment.setCurrentValue(0.0);

        // Salva o investimento
        Investment savedInvestment = investmentRepository.save(investment);
        log.info("Novo investimento adicionado: {}, ID: {}", investment.getInvestmentType(), savedInvestment.getId());

        return savedInvestment;
    }

    @Override
    public void removeInvestment(UUID investmentId) throws InvestmentException {
        Investment investment = investmentRepository.findById(investmentId)
                .orElseThrow(() -> new InvestmentException("Investment não encontrado com ID: " + investmentId));

        try {
            investmentRepository.delete(investment);
            log.info("Investment removido: {}, ID: {}", investment.getInvestmentType(), investmentId);
        } catch (Exception e) {
            log.error("Erro ao remover Investment '{}' (ID: {}): {}", investment.getInvestmentType(), investmentId, e.getMessage());
            throw new InvestmentException("Erro ao remover o investimento: " + investment.getInvestmentType(), e);
        }
    }
}
