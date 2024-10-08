package com.InvestmentsTracker.investment_portfolio.service.other;

import com.InvestmentsTracker.investment_portfolio.exception.InvestmentException;
import com.InvestmentsTracker.investment_portfolio.exception.OtherPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.model.Other;
import com.InvestmentsTracker.investment_portfolio.repository.InvestmentRepository;
import com.InvestmentsTracker.investment_portfolio.repository.OtherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Implementação do serviço específico para investimentos em "Other".
 */
@Service
@Slf4j
public class OtherInvestmentServiceImpl implements OtherInvestmentService {

    private final OtherService otherService;
    private final OtherRepository otherRepository;

    @Autowired
    public OtherInvestmentServiceImpl(OtherService otherService, OtherRepository otherRepository) {
        this.otherService = otherService;
        this.otherRepository = otherRepository;

    }

    /**
     * Atualiza manualmente o valor de um investimento em "Other" específico.
     *
     * @param investmentId ID do investimento em "Other".
     * @param newValue Novo valor atual em EUR.
     * @throws OtherPriceRetrievalException Se ocorrer um erro ao atualizar o valor.
     */
    @Override
    public void updateValue(UUID investmentId, double newValue) throws OtherPriceRetrievalException {
        Other other = otherRepository.findById(investmentId)
                .orElseThrow(() -> new OtherPriceRetrievalException("Investimento 'Other' não encontrado com ID: " + investmentId));

        otherService.updateOtherValue(other, newValue);
        otherRepository.save(other);

        log.info("Investimento 'Other' '{}' (ID: {}) atualizado com novo valor: {} EUR", other.getDescription(), investmentId, newValue);
    }

    /**
     * Atualiza os valores de todos os investimentos em "Other" no portfólio do usuário.
     *
     * @param portfolioId ID do portfólio do usuário.
     * @param newValue Novo valor atual a ser definido para cada "Other".
     * @throws OtherPriceRetrievalException Se ocorrer um erro ao atualizar os valores.
     */
    @Override
    public void updateAllValues(UUID portfolioId, double newValue) throws OtherPriceRetrievalException {
        List<Other> otherList = otherRepository.findByPortfolioId(portfolioId);
        for (Other other : otherList) {
            try {
                updateValue(other.getId(), newValue);
            } catch (OtherPriceRetrievalException e) {
                log.error("Erro ao atualizar investimento 'Other' ID {}: {}", other.getId(), e.getMessage(), e);
                // Dependendo da necessidade, pode-se optar por continuar ou interromper
                throw e;
            }
        }
        log.info("Todos os investimentos 'Other' no portfólio ID {} foram atualizados com o valor: {} EUR", portfolioId, newValue);
    }

    /**
     * Recupera o valor atual de um investimento em "Other".
     *
     * @param investmentId ID do investimento em "Other".
     * @return Valor atual em EUR.
     * @throws OtherPriceRetrievalException Se ocorrer um erro ao recuperar o valor.
     */
    @Override
    public double getCurrentValue(UUID investmentId) throws InvestmentException {
        Other other = otherRepository.findById(investmentId)
                .orElseThrow(() -> new InvestmentException("Investment 'Other' não encontrado com ID: " + investmentId));

        double currentValue = otherService.calculateCurrentValue(other);
        other.setCurrentValue(currentValue);
        otherRepository.save(other);

        log.info("Valor atual para Investment 'Other' '{}' (ID: {}): {} EUR", other.getDescription(), investmentId, currentValue);
        return currentValue;
    }

    /**
     * Recupera todas as instâncias de "Other" associadas a um usuário específico.
     *
     * @param userId ID do usuário.
     * @return Lista de investimentos em "Other".
     */
    @Override
    public List<Other> getAllOthersByUser(UUID userId) {
        List<Other> otherList = otherRepository.findByPortfolioUserId(userId);
        log.info("Encontrados {} investimentos 'Other' para o usuário ID: {}", otherList.size(), userId);
        return otherList;
    }

    /**
     * Adiciona um novo investimento em "Other".
     *
     * @param other Instância de Other a ser adicionada.
     * @return Other criado.
     */
    @Override
    public Other addOther(Other other) {
        Other savedOther = otherRepository.save(other);
        log.info("Novo investimento 'Other' adicionado: '{}', ID: {}", savedOther.getDescription(), savedOther.getId());
        return savedOther;
    }

    /**
     * Remove um investimento em "Other" específico.
     *
     * @param investmentId ID do investimento em "Other".
     * @throws OtherPriceRetrievalException Se ocorrer um erro ao remover o "Other".
     */
    @Override
    public void removeOther(UUID investmentId) throws OtherPriceRetrievalException {
        Other other = otherRepository.findById(investmentId)
                .orElseThrow(() -> new OtherPriceRetrievalException("Investimento 'Other' não encontrado com ID: " + investmentId));

        otherRepository.delete(other);
        log.info("Investimento 'Other' removido: '{}', ID: {}", other.getDescription(), investmentId);
    }

}
