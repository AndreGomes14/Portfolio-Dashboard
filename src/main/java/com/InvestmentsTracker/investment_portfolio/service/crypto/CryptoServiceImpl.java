// src/main/java/com/InvestmentsTracker/investment_portfolio/service/crypto/CryptoServiceImpl.java

package com.InvestmentsTracker.investment_portfolio.service.crypto;

import com.InvestmentsTracker.investment_portfolio.exception.CryptoPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.model.Crypto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Implementação do CryptoService que permite a atualização automática do valor dos Cryptos via API do Yahoo Finance.
 */
@Service
@Slf4j
public class CryptoServiceImpl implements CryptoService {

    /**
     * Atualiza o valor atual de um investimento em Crypto utilizando a API do Yahoo Finance.
     *
     * @param crypto Instância de Crypto a ser atualizada.
     * @throws CryptoPriceRetrievalException Se ocorrer um erro ao buscar o preço.
     */
    @Override
    public void updateCryptoValueFromApi(Crypto crypto) throws CryptoPriceRetrievalException {
        String ticker = crypto.getTicker();
        try {
            // Utiliza o nome totalmente qualificado para evitar conflito de nomes
            yahoofinance.Stock yahooStock = YahooFinance.get(ticker);
            if (yahooStock == null || yahooStock.getQuote() == null) {
                log.error("Nenhum dado encontrado para o ticker: {}", ticker);
                throw new CryptoPriceRetrievalException("Nenhum dado encontrado para o ticker: " + ticker);
            }

            BigDecimal price = yahooStock.getQuote().getPrice();
            if (price == null) {
                log.error("Preço atual não disponível para o ticker: {}", ticker);
                throw new CryptoPriceRetrievalException("Preço atual não disponível para o ticker: " + ticker);
            }

            double currentPrice = price.doubleValue();
            double totalCurrentValue = currentPrice * crypto.getUnits();
            crypto.setCurrentValue(totalCurrentValue);
            crypto.setLastSyncedPrice(currentPrice);
            log.info("Valor atualizado automaticamente para Crypto (Ticker: {}): {} EUR", ticker, crypto.getCurrentValue());
        } catch (IOException e) {
            log.error("Erro ao buscar preço para o ticker {}: {}", ticker, e.getMessage(), e);
            throw new CryptoPriceRetrievalException("Erro ao buscar preço para o ticker: " + ticker, e);
        } catch (NullPointerException e) {
            log.error("Dados de preço ausentes para o ticker: {}", ticker, e);
            throw new CryptoPriceRetrievalException("Dados de preço ausentes para o ticker: " + ticker, e);
        }
    }
}
