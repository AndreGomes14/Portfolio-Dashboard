// src/main/java/com/InvestmentsTracker/investment_portfolio/service/EtfServiceImpl.java
package com.InvestmentsTracker.investment_portfolio.service;

import com.InvestmentsTracker.investment_portfolio.exception.EtfPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.model.Etf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementação do EtfService que permite a atualização manual do valor dos ETFs.
 */
@Service
@Slf4j
public class EtfServiceImpl implements EtfService {

    /**
     * Atualiza manualmente o valor atual de um investimento em ETF.
     *
     * @param etf Instância de Etf a ser atualizada.
     * @param newValue Novo valor atual em EUR.
     * @throws EtfPriceRetrievalException Se o novo valor for inválido.
     */
    @Override
    public void updateEtfValue(Etf etf, double newValue) throws EtfPriceRetrievalException {
        if (newValue < 0) {
            log.error("Tentativa de definir um valor negativo para ETF ID {}: {}", etf.getId(), newValue);
            throw new EtfPriceRetrievalException("O valor atual não pode ser negativo.");
        }

        etf.setCurrentValue(newValue);
        log.info("Valor atualizado manualmente para ETF '{}' (ID: {}): {} EUR", etf.getDescription(), etf.getId(), newValue);
    }

    // Implementações de outros métodos, se houver
}
