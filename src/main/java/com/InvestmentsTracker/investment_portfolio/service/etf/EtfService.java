// src/main/java/com/InvestmentsTracker/investment_portfolio/service/EtfService.java
package com.InvestmentsTracker.investment_portfolio.service.etf;

import com.InvestmentsTracker.investment_portfolio.exception.EtfPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.model.Etf;

/**
 * Interface para serviços de domínio que gerenciam operações relacionadas a ETFs.
 */
public interface EtfService {

    /**
     * Atualiza manualmente o valor atual de um investimento em ETF.
     *
     * @param etf Instância de Etf a ser atualizada.
     * @param newValue Novo valor atual em EUR.
     * @throws EtfPriceRetrievalException Se ocorrer um erro ao atualizar o valor.
     */
    void updateEtfValue(Etf etf, double newValue) throws EtfPriceRetrievalException;

    /**
     * Outros métodos relacionados a operações de ETFs podem ser adicionados aqui.
     */
}
