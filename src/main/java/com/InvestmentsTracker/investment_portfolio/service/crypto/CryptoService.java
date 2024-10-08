// src/main/java/com/InvestmentsTracker/investment_portfolio/service/crypto/CryptoService.java

package com.InvestmentsTracker.investment_portfolio.service.crypto;

import com.InvestmentsTracker.investment_portfolio.exception.CryptoPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.model.Crypto;

/**
 * Interface para serviços de domínio que gerenciam operações relacionadas a Crypto.
 */
public interface CryptoService {

    /**
     * Atualiza o valor atual de um investimento em Crypto utilizando uma API externa.
     *
     * @param crypto Instância de Crypto a ser atualizada.
     * @throws CryptoPriceRetrievalException Se ocorrer um erro ao buscar o preço.
     */
    void updateCryptoValueFromApi(Crypto crypto) throws CryptoPriceRetrievalException;

    // Outros métodos relacionados a Crypto podem ser adicionados aqui
}
