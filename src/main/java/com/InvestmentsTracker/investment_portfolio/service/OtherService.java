// src/main/java/com/InvestmentsTracker/investment_portfolio/service/OtherService.java
package com.InvestmentsTracker.investment_portfolio.service;

import com.InvestmentsTracker.investment_portfolio.exception.OtherPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.model.Other;

/**
 * Interface para serviços de domínio que gerenciam operações relacionadas a Investimentos "Other".
 */
public interface OtherService {

    /**
     * Atualiza manualmente o valor atual de um investimento "Other".
     *
     * @param other Instância de Other a ser atualizada.
     * @param newValue Novo valor atual em EUR.
     * @throws OtherPriceRetrievalException Se ocorrer um erro ao atualizar o valor.
     */
    void updateOtherValue(Other other, double newValue) throws OtherPriceRetrievalException;

    /**
     * Outros métodos relacionados a operações de Investimentos "Other" podem ser adicionados aqui.
     */
}
