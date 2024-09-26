// src/main/java/com/InvestmentsTracker/investment_portfolio/service/CryptoServiceImpl.java
package com.InvestmentsTracker.investment_portfolio.service;

import com.InvestmentsTracker.investment_portfolio.exception.CryptoPriceRetrievalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;

/**
 * Implementação do CryptoService utilizando a biblioteca YahooFinanceAPI.
 */
@Service
@Slf4j
public class CryptoServiceImpl implements CryptoService {

    @Override
    public double getCryptoPriceInEUR(String ticker) throws CryptoPriceRetrievalException {
        if (ticker == null || ticker.trim().isEmpty()) {
            log.error("Ticker fornecido está vazio ou nulo.");
            throw new CryptoPriceRetrievalException("Ticker não pode ser vazio.");
        }

        try {
            log.debug("Buscando preço para o ticker: {}", ticker);

            // O YahooFinanceAPI pode não suportar diretamente criptomoedas; ajuste conforme necessário.
            // Alternativamente, use um provedor que suporte criptomoedas ou utilize uma API específica.
            Stock stock = YahooFinance.get(ticker);
            if (stock == null) {
                log.error("Ticker desconhecido ou não encontrado: {}", ticker);
                throw new CryptoPriceRetrievalException("Ticker desconhecido ou não encontrado: " + ticker);
            }

            double price = stock.getQuote().getPrice().doubleValue();
            log.info("Preço atual para {}: {} EUR", ticker, price);
            return price;

        } catch (IOException e) {
            log.error("Erro ao conectar com o Yahoo Finance para o ticker: {}", ticker, e);
            throw new CryptoPriceRetrievalException("Erro ao recuperar o preço da criptomoeda: " + ticker, e);
        } catch (NullPointerException e) {
            log.error("Dados de preço ausentes para o ticker: {}", ticker, e);
            throw new CryptoPriceRetrievalException("Dados de preço ausentes para o ticker: " + ticker, e);
        }
    }
}
