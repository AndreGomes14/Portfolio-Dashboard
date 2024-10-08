package com.InvestmentsTracker.investment_portfolio.service.stock;

import com.InvestmentsTracker.investment_portfolio.exception.StockPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.model.Stock;

/**
 * Interface para serviços de domínio que gerenciam operações relacionadas a Stocks.
 */
public interface StockService {

    /**
     * Atualiza o valor atual de um investimento em Stock utilizando a API do Yahoo Finance.
     *
     * @param stock Instância de Stock a ser atualizada.
     * @throws StockPriceRetrievalException Se ocorrer um erro ao buscar o preço.
     */
    void updateStockValueFromApi(Stock stock) throws StockPriceRetrievalException;

    /**
     * Obtém o preço atual de uma ação em EUR.
     *
     * @param ticker Ticker da ação (ex: "AAPL").
     * @return Preço atual em EUR.
     * @throws StockPriceRetrievalException Se ocorrer um erro ao recuperar o preço.
     */
    double getStockPriceInEUR(String ticker) throws StockPriceRetrievalException;
}
