package com.InvestmentsTracker.investment_portfolio.service.savings;

import com.InvestmentsTracker.investment_portfolio.exception.DepositPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.model.Savings;
import com.InvestmentsTracker.investment_portfolio.repository.SavingsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementação do serviço específico para investimentos em Savings (Depósitos).
 */
@Service
@Slf4j
public class SavingsInvestmentServiceImpl implements SavingsInvestmentService {

    private final SavingsService depositService;
    private final SavingsRepository savingsRepository;

    @Autowired
    public SavingsInvestmentServiceImpl(SavingsService depositService, SavingsRepository savingsRepository) {
        this.depositService = depositService;
        this.savingsRepository = savingsRepository;
    }

    /**
     * Atualiza manualmente o valor de um investimento em Savings específico.
     *
     * @param investmentId ID do investimento em Savings.
     * @param newValue Novo valor atual em EUR.
     * @throws DepositPriceRetrievalException Se ocorrer um erro ao atualizar o valor.
     */
    @Override
    public void updateValue(Long investmentId, double newValue) throws DepositPriceRetrievalException {
        Savings savings = savingsRepository.findById(investmentId)
                .orElseThrow(() -> new DepositPriceRetrievalException("Savings não encontrado com ID: " + investmentId));

        depositService.updateSavingsValue(savings, newValue);
        savingsRepository.save(savings);

        log.info("Savings '{}' (ID: {}) atualizado com novo valor: {} EUR", savings.getDescription(), investmentId, newValue);
    }

    /**
     * Atualiza os valores de todos os investimentos em Savings no portfólio do usuário.
     *
     * @param portfolioId ID do portfólio do usuário.
     * @param newValue Novo valor atual a ser definido para cada Savings.
     * @throws DepositPriceRetrievalException Se ocorrer um erro ao atualizar os valores.
     */
    @Override
    public void updateAllValues(Long portfolioId, double newValue) throws DepositPriceRetrievalException {
        List<Savings> savingsList = savingsRepository.findByPortfolioId(portfolioId);
        for (Savings savings : savingsList) {
            try {
                updateValue(savings.getId(), newValue);
            } catch (DepositPriceRetrievalException e) {
                log.error("Erro ao atualizar Savings ID {}: {}", savings.getId(), e.getMessage(), e);
                // Dependendo da necessidade, pode-se optar por continuar ou interromper
                throw e;
            }
        }
        log.info("Todos os Savings no portfólio ID {} foram atualizados com o valor: {} EUR", portfolioId, newValue);
    }

    /**
     * Recupera o valor atual de um investimento em Savings.
     *
     * @param investmentId ID do investimento em Savings.
     * @return Valor atual em EUR.
     * @throws DepositPriceRetrievalException Se ocorrer um erro ao recuperar o valor.
     */
    @Override
    public double getCurrentValue(Long investmentId) throws DepositPriceRetrievalException {
        Savings savings = savingsRepository.findById(investmentId)
                .orElseThrow(() -> new DepositPriceRetrievalException("Savings não encontrado com ID: " + investmentId));

        double currentValue = savings.getCurrentValue();
        log.info("Valor atual para Savings '{}' (ID: {}): {} EUR", savings.getDescription(), investmentId, currentValue);
        return currentValue;
    }

    /**
     * Recupera todas as instâncias de Savings associadas a um usuário específico.
     *
     * @param userId ID do usuário.
     * @return Lista de investimentos em Savings.
     */
    @Override
    public List<Savings> getAllDepositsByUser(Long userId) {
        List<Savings> savingsList = savingsRepository.findByPortfolioUserId(userId);
        log.info("Encontradas {} Savings para o usuário ID: {}", savingsList.size(), userId);
        return savingsList;
    }

    @Override
    public Savings addSavings(Savings savings) {
        Savings savedSavings = savingsRepository.save(savings);
        log.info("Novo Savings adicionado: '{}', ID: {}", savedSavings.getDescription(), savedSavings.getId());
        return savedSavings;
    }

    /**
     * Remove um investimento em Savings específico.
     *
     * @param investmentId ID do investimento em Savings.
     * @throws DepositPriceRetrievalException Se ocorrer um erro ao remover o Savings.
     */
    @Override
    public void removeSavings(Long investmentId) throws DepositPriceRetrievalException {
        Savings savings = savingsRepository.findById(investmentId)
                .orElseThrow(() -> new DepositPriceRetrievalException("Savings não encontrado com ID: " + investmentId));

        savingsRepository.delete(savings);
        log.info("Savings removido: '{}', ID: {}", savings.getDescription(), investmentId);
    }
}
