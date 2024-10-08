package com.InvestmentsTracker.investment_portfolio.service.etf;

import com.InvestmentsTracker.investment_portfolio.exception.EtfPriceRetrievalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;

/**
 * Implementação do EtfPriceService utilizando a biblioteca YahooFinanceAPI.
 */
@Service
@Slf4j
public class EtfPriceServiceImpl implements EtfPriceService {

    @Override
    public double getEtfPriceInEUR(String ticker) throws EtfPriceRetrievalException {
        if (ticker == null || ticker.trim().isEmpty()) {
            log.error("Ticker fornecido está vazio ou nulo.");
            throw new EtfPriceRetrievalException("Ticker não pode ser vazio.");
        }

        try {
            log.debug("Buscando preço para o ticker: {}", ticker);

            // O YahooFinanceAPI pode não suportar diretamente Etfs em EUR; ajuste conforme necessário.
            // Alternativamente, use um provedor que suporte Etfs em EUR ou utilize uma API específica.
            Stock stock = YahooFinance.get(ticker + "-EUR"); // Exemplo: SPY-EUR
            if (stock == null || stock.getQuote() == null || stock.getQuote().getPrice() == null) {
                log.error("Ticker desconhecido ou não encontrado: {}", ticker);
                throw new EtfPriceRetrievalException("Ticker desconhecido ou não encontrado: " + ticker);
            }

            double price = stock.getQuote().getPrice().doubleValue();
            log.info("Preço atual para {}: {} EUR", ticker, price);
            return price;

        } catch (IOException e) {
            log.error("Erro ao conectar com o Yahoo Finance para o ticker: {}", ticker, e);
            throw new EtfPriceRetrievalException("Erro ao recuperar o preço da Etf: " + ticker, e);
        } catch (NullPointerException e) {
            log.error("Dados de preço ausentes para o ticker: {}", ticker, e);
            throw new EtfPriceRetrievalException("Dados de preço ausentes para o ticker: " + ticker, e);
        }
    }
}
