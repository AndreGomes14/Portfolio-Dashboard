// src/main/java/com/InvestmentsTracker/investment_portfolio/service/stock/StockInvestmentService.java

package com.InvestmentsTracker.investment_portfolio.service.stock;

import com.InvestmentsTracker.investment_portfolio.dto.stock.StockRequestDTO;
import com.InvestmentsTracker.investment_portfolio.exception.InvestmentException;
import com.InvestmentsTracker.investment_portfolio.exception.StockPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.model.Stock;

import java.util.List;
import java.util.UUID;

/**
 * Interface para serviços específicos de investimentos em Stocks.
 */
public interface StockInvestmentService {

    /**
     * Atualiza o preço de uma ação específica.
     *
     * @param investmentId ID do investimento em ação.
     * @return Preço atualizado em EUR.
     * @throws StockPriceRetrievalException Se ocorrer um erro ao atualizar o preço.
     */
    double updatePrice(UUID investmentId) throws StockPriceRetrievalException;

    /**
     * Atualiza os preços de todas as ações no portfólio do usuário.
     *
     * @param portfolioId ID do portfólio do usuário.
     * @throws StockPriceRetrievalException Se ocorrer um erro ao atualizar os preços.
     */
    void updateAllPrices(UUID portfolioId) throws StockPriceRetrievalException;

    /**
     * Calcula o valor atual de uma ação no investimento.
     *
     * @param investmentId ID do investimento em ação.
     * @return Valor atual em EUR.
     * @throws InvestmentException Se ocorrer um erro ao recuperar o valor.
     */
    double getCurrentValue(UUID investmentId) throws InvestmentException;

    /**
     * Recupera todas as ações associadas a um usuário.
     *
     * @param userId ID do usuário.
     * @return Lista de ações.
     */
    List<Stock> getAllStocksByUser(UUID userId);

    /**
     * Adiciona uma nova ação ao portfólio e atualiza seu valor atual via API.
     *
     * @param stockRequestDTO DTO contendo os dados da ação.
     * @return Ação adicionada com valor atualizado.
     * @throws StockPriceRetrievalException Se ocorrer um erro ao atualizar o valor.
     * @throws InvestmentException Se ocorrer um erro relacionado ao investimento.
     */
    Stock addStock(StockRequestDTO stockRequestDTO) throws StockPriceRetrievalException, InvestmentException;

    /**
     * Remove uma ação específica do portfólio.
     *
     * @param investmentId ID da ação a ser removida.
     * @throws StockPriceRetrievalException Se ocorrer um erro ao remover a ação.
     */
    void removeStock(UUID investmentId) throws StockPriceRetrievalException;
}
