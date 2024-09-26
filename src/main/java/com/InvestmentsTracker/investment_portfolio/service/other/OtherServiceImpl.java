package com.InvestmentsTracker.investment_portfolio.service.other;

import com.InvestmentsTracker.investment_portfolio.exception.OtherPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.model.Other;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementação do OtherService que permite a atualização manual do valor dos Investimentos "Other".
 */
@Service
@Slf4j
public class OtherServiceImpl implements OtherService {

    /**
     * Atualiza manualmente o valor atual de um investimento "Other".
     *
     * @param other Instância de Other a ser atualizada.
     * @param newValue Novo valor atual em EUR.
     * @throws OtherPriceRetrievalException Se o novo valor for inválido.
     */
    @Override
    public void updateOtherValue(Other other, double newValue) throws OtherPriceRetrievalException {
        if (newValue < 0) {
            log.error("Tentativa de definir um valor negativo para Investimento 'Other' ID {}: {}", other.getId(), newValue);
            throw new OtherPriceRetrievalException("O valor atual não pode ser negativo.");
        }

        other.setCurrentValue(newValue);
        log.info("Valor atualizado manualmente para Investimento 'Other' '{}' (ID: {}): {} EUR", other.getDescription(), other.getId(), newValue);
    }

    // Implementações de outros métodos, se houver
}
