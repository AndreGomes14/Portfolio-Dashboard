package com.InvestmentsTracker.investment_portfolio.service;

import com.InvestmentsTracker.investment_portfolio.exception.StockPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.model.Stock;
import com.InvestmentsTracker.investment_portfolio.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementação do serviço específico para investimentos em Stocks.
 */
@Service
@Slf4j
public class StockInvestmentServiceImpl implements StockInvestmentService {

    private final StockService stockService;
    private final StockRepository stockRepository;

    @Autowired
    public StockInvestmentServiceImpl(StockService stockService, StockRepository stockRepository) {
        this.stockService = stockService;
        this.stockRepository = stockRepository;
    }

    /**
     * Atualiza o valor de um investimento em Stock específico utilizando a API do Yahoo Finance.
     *
     * @param investmentId ID do investimento em Stock.
     * @throws StockPriceRetrievalException Se ocorrer um erro ao atualizar o valor.
     */
    @Override
    public void updateValue(Long investmentId) throws StockPriceRetrievalException {
        Stock stock = stockRepository.findById(investmentId)
                .orElseThrow(() -> new StockPriceRetrievalException("Stock não encontrado com ID: " + investmentId));

        stockService.updateStockValueFromApi(stock);
        stockRepository.save(stock);

        log.info("Stock '{}' (Ticker: {}, ID: {}) atualizado com novo valor: {} EUR",
                stock.getTicker(), stock.getTicker(), investmentId, stock.getCurrentValue());
    }

    /**
     * Atualiza os valores de todos os investimentos em Stocks no portfólio do usuário utilizando a API do Yahoo Finance.
     *
     * @param portfolioId ID do portfólio do usuário.
     * @throws StockPriceRetrievalException Se ocorrer um erro ao atualizar os valores.
     */
    @Override
    public void updateAllValues(Long portfolioId) throws StockPriceRetrievalException {
        List<Stock> stockList = stockRepository.findByPortfolioId(portfolioId);
        for (Stock stock : stockList) {
            try {
                updateValue(stock.getId());
            } catch (StockPriceRetrievalException e) {
                log.error("Erro ao atualizar Stock ID {}: {}", stock.getId(), e.getMessage(), e);
                // Dependendo da necessidade, pode-se optar por continuar ou interromper
                throw e;
            }
        }
        log.info("Todos os Stocks no portfólio ID {} foram atualizados.", portfolioId);
    }

    /**
     * Recupera o valor atual de um investimento em Stock.
     *
     * @param investmentId ID do investimento em Stock.
     * @return Valor atual em EUR.
     * @throws StockPriceRetrievalException Se ocorrer um erro ao recuperar o valor.
     */
    @Override
    public double getCurrentValue(Long investmentId) throws StockPriceRetrievalException {
        Stock stock = stockRepository.findById(investmentId)
                .orElseThrow(() -> new StockPriceRetrievalException("Stock não encontrado com ID: " + investmentId));

        // Atualiza o valor antes de retornar
        stockService.updateStockValueFromApi(stock);
        stockRepository.save(stock);

        log.info("Valor atual para Stock '{}' (Ticker: {}, ID: {}): {} EUR",
                stock.getTicker(), stock.getTicker(), investmentId, stock.getCurrentValue());
        return stock.getCurrentValue();
    }

    /**
     * Recupera todas as instâncias de Stocks associadas a um usuário específico.
     *
     * @param userId ID do usuário.
     * @return Lista de investimentos em Stocks.
     */
    @Override
    public List<Stock> getAllStocksByUser(Long userId) {
        List<Stock> stockList = stockRepository.findByPortfolioUserId(userId);
        log.info("Encontrados {} Stocks para o usuário ID: {}", stockList.size(), userId);
        return stockList;
    }

    /**
     * Adiciona um novo investimento em Stock e atualiza seu valor atual via API.
     *
     * @param stock Instância de Stock a ser adicionada.
     * @return Stock criado com valor atualizado.
     * @throws StockPriceRetrievalException Se ocorrer um erro ao atualizar o valor.
     */
    @Override
    public Stock addStock(Stock stock) throws StockPriceRetrievalException {
        // Atualiza o valor atual utilizando a API antes de salvar
        stockService.updateStockValueFromApi(stock);
        Stock savedStock = stockRepository.save(stock);
        log.info("Novo Stock adicionado: '{}' (Ticker: {}), ID: {}", savedStock.getTicker(), savedStock.getTicker(), savedStock.getId());
        return savedStock;
    }

    /**
     * Remove um investimento em Stock específico.
     *
     * @param investmentId ID do investimento em Stock.
     * @throws StockPriceRetrievalException Se ocorrer um erro ao remover o Stock.
     */
    @Override
    public void removeStock(Long investmentId) throws StockPriceRetrievalException {
        Stock stock = stockRepository.findById(investmentId)
                .orElseThrow(() -> new StockPriceRetrievalException("Stock não encontrado com ID: " + investmentId));

        stockRepository.delete(stock);
        log.info("Stock removido: '{}' (Ticker: {}), ID: {}", stock.getTicker(), stock.getTicker(), investmentId);
    }
}
