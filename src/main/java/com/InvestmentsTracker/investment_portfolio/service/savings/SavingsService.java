// src/main/java/com/InvestmentsTracker/investment_portfolio/service/DepositService.java
package com.InvestmentsTracker.investment_portfolio.service.savings;

import com.InvestmentsTracker.investment_portfolio.exception.DepositPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.model.Savings;

/**
 * Interface para serviços de domínio que gerenciam operações relacionadas a Savings (Depósitos).
 */
public interface SavingsService {

    /**
     * Atualiza manualmente o valor atual de um investimento em Savings.
     *
     * @param savings Instância de Savings a ser atualizada.
     * @param newValue Novo valor atual em EUR.
     * @throws DepositPriceRetrievalException Se ocorrer um erro ao atualizar o valor.
     */
    void updateSavingsValue(Savings savings, double newValue) throws DepositPriceRetrievalException;

    /**
     * Outros métodos relacionados a operações de Savings podem ser adicionados aqui.
     */
}
