package com.InvestmentsTracker.investment_portfolio.service.stock;

import com.InvestmentsTracker.investment_portfolio.exception.StockPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.model.Stock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Implementação do StockService que permite a atualização automática do valor dos Stocks via API do Yahoo Finance.
 */
@Service
@Slf4j
public class StockServiceImpl implements StockService {

    /**
     * Atualiza o valor atual de um investimento em Stock utilizando a API do Yahoo Finance.
     *
     * @param stock Instância de Stock a ser atualizada.
     * @throws StockPriceRetrievalException Se ocorrer um erro ao buscar o preço.
     */
    @Override
    public void updateStockValueFromApi(Stock stock) throws StockPriceRetrievalException {
        String ticker = stock.getTicker();
        try {
            // Utiliza o nome totalmente qualificado para evitar conflito de nomes
            yahoofinance.Stock yahooStock = YahooFinance.get(ticker);
            if (yahooStock == null || yahooStock.getQuote() == null) {
                log.error("Nenhum dado encontrado para o ticker: {}", ticker);
                throw new StockPriceRetrievalException("Nenhum dado encontrado para o ticker: " + ticker);
            }

            BigDecimal price = yahooStock.getQuote().getPrice();
            if (price == null) {
                log.error("Preço atual não disponível para o ticker: {}", ticker);
                throw new StockPriceRetrievalException("Preço atual não disponível para o ticker: " + ticker);
            }

            double currentPrice = price.doubleValue();
            double totalCurrentValue = currentPrice * stock.getUnits();
            stock.setCurrentValue(totalCurrentValue);
            stock.setLastSyncedPrice(currentPrice);
            log.info("Valor atualizado automaticamente para Stock '{}' (Ticker: {}): {} EUR",
                    stock.getTicker(), ticker, stock.getCurrentValue());
        } catch (IOException e) {
            log.error("Erro ao buscar preço para o ticker {}: {}", ticker, e.getMessage(), e);
            throw new StockPriceRetrievalException("Erro ao buscar preço para o ticker: " + ticker, e);
        } catch (NullPointerException e) {
            log.error("Dados de preço ausentes para o ticker: {}", ticker, e);
            throw new StockPriceRetrievalException("Dados de preço ausentes para o ticker: " + ticker, e);
        }
    }

    /**
     * Obtém o preço atual de uma ação em EUR.
     *
     * @param ticker Ticker da ação (ex: "AAPL").
     * @return Preço atual em EUR.
     * @throws StockPriceRetrievalException Se ocorrer um erro ao recuperar o preço.
     */
    @Override
    public double getStockPriceInEUR(String ticker) throws StockPriceRetrievalException {
        if (ticker == null || ticker.trim().isEmpty()) {
            log.error("Ticker fornecido está vazio ou nulo.");
            throw new StockPriceRetrievalException("Ticker não pode ser vazio.");
        }

        try {
            log.debug("Buscando preço para o ticker: {}", ticker);

            // Utiliza o nome totalmente qualificado para evitar conflito de nomes
            yahoofinance.Stock yahooStock = YahooFinance.get(ticker);
            if (yahooStock == null || yahooStock.getQuote() == null || yahooStock.getQuote().getPrice() == null) {
                log.error("Ticker desconhecido ou não encontrado: {}", ticker);
                throw new StockPriceRetrievalException("Ticker desconhecido ou não encontrado: " + ticker);
            }

            double price = yahooStock.getQuote().getPrice().doubleValue();
            log.info("Preço atual para {}: {} EUR", ticker, price);
            return price;

        } catch (IOException e) {
            log.error("Erro ao conectar com o Yahoo Finance para o ticker: {}", ticker, e);
            throw new StockPriceRetrievalException("Erro ao recuperar o preço da ação: " + ticker, e);
        } catch (NullPointerException e) {
            log.error("Dados de preço ausentes para o ticker: {}", ticker, e);
            throw new StockPriceRetrievalException("Dados de preço ausentes para o ticker: " + ticker, e);
        }
    }
}
