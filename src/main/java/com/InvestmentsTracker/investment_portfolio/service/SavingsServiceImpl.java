// src/main/java/com/InvestmentsTracker/investment_portfolio/service/DepositServiceImpl.java
package com.InvestmentsTracker.investment_portfolio.service;

import com.InvestmentsTracker.investment_portfolio.exception.DepositPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.model.Savings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementação do DepositService que permite a atualização manual do valor dos Savings (Depósitos).
 */
@Service
@Slf4j
public class SavingsServiceImpl implements SavingsService {

    /**
     * Atualiza manualmente o valor atual de um investimento em Savings.
     *
     * @param savings Instância de Savings a ser atualizada.
     * @param newValue Novo valor atual em EUR.
     * @throws DepositPriceRetrievalException Se o novo valor for inválido.
     */
    @Override
    public void updateSavingsValue(Savings savings, double newValue) throws DepositPriceRetrievalException {
        if (newValue < 0) {
            log.error("Tentativa de definir um valor negativo para Savings ID {}: {}", savings.getId(), newValue);
            throw new DepositPriceRetrievalException("O valor atual não pode ser negativo.");
        }

        savings.setCurrentValue(newValue);
        log.info("Valor atualizado manualmente para Savings '{}' (ID: {}): {} EUR", savings.getDescription(), savings.getId(), newValue);
    }

    // Implementações de outros métodos, se houver
}
