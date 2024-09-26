package com.InvestmentsTracker.investment_portfolio.service.stock;

import com.InvestmentsTracker.investment_portfolio.exception.StockPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.model.Stock;

import java.util.List;

/**
 * Interface para serviços específicos de investimentos em Stocks.
 */
public interface StockInvestmentService {

    /**
     * Atualiza manualmente o valor de um investimento em Stock específico.
     *
     * @param investmentId ID do investimento em Stock.
     * @throws StockPriceRetrievalException Se ocorrer um erro ao atualizar o valor.
     */
    void updateValue(Long investmentId) throws StockPriceRetrievalException;

    /**
     * Atualiza os valores de todos os investimentos em Stocks no portfólio do usuário.
     *
     * @param portfolioId ID do portfólio do usuário.
     * @throws StockPriceRetrievalException Se ocorrer um erro ao atualizar os valores.
     */
    void updateAllValues(Long portfolioId) throws StockPriceRetrievalException;

    /**
     * Recupera o valor atual de um investimento em Stock.
     *
     * @param investmentId ID do investimento em Stock.
     * @return Valor atual em EUR.
     * @throws StockPriceRetrievalException Se ocorrer um erro ao recuperar o valor.
     */
    double getCurrentValue(Long investmentId) throws StockPriceRetrievalException;

    /**
     * Recupera todas as instâncias de Stocks associadas a um usuário específico.
     *
     * @param userId ID do usuário.
     * @return Lista de investimentos em Stocks.
     */
    List<Stock> getAllStocksByUser(Long userId);

    /**
     * Adiciona um novo investimento em Stock e atualiza seu valor atual via API.
     *
     * @param stock Instância de Stock a ser adicionada.
     * @return Stock criado com valor atualizado.
     * @throws StockPriceRetrievalException Se ocorrer um erro ao atualizar o valor.
     */
    Stock addStock(Stock stock) throws StockPriceRetrievalException;

    /**
     * Remove um investimento em Stock específico.
     *
     * @param investmentId ID do investimento em Stock.
     * @throws StockPriceRetrievalException Se ocorrer um erro ao remover o Stock.
     */
    void removeStock(Long investmentId) throws StockPriceRetrievalException;
}
