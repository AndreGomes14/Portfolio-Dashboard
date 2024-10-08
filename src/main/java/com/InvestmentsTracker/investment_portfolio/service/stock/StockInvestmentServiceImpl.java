package com.InvestmentsTracker.investment_portfolio.service.stock;

import com.InvestmentsTracker.investment_portfolio.dto.stock.StockRequestDTO;
import com.InvestmentsTracker.investment_portfolio.exception.InvestmentException;
import com.InvestmentsTracker.investment_portfolio.exception.StockPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.mapper.StockMapper;
import com.InvestmentsTracker.investment_portfolio.model.Stock;
import com.InvestmentsTracker.investment_portfolio.repository.StockRepository;
import com.InvestmentsTracker.investment_portfolio.service.portfolio.PortfolioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Implementação do serviço específico para investimentos em Stocks.
 */
@Service
@Slf4j
public class StockInvestmentServiceImpl implements StockInvestmentService {

    private final StockService stockService;
    private final StockRepository stockRepository;
    private final PortfolioService portfolioService; // Serviço para gerenciar portfólios
    private final StockMapper stockMapper;

    @Autowired
    public StockInvestmentServiceImpl(StockService stockService,
                                      StockRepository stockRepository,
                                      PortfolioService portfolioService,
                                      StockMapper stockMapper) {
        this.stockService = stockService;
        this.stockRepository = stockRepository;
        this.portfolioService = portfolioService;
        this.stockMapper = stockMapper;
    }

    /**
     * Atualiza o preço de uma ação específica no investimento.
     *
     * @param investmentId ID do investimento em ação.
     * @return Preço atualizado em EUR.
     * @throws StockPriceRetrievalException Se ocorrer um erro ao recuperar o preço.
     */
    @Override
    public double updatePrice(UUID investmentId) throws StockPriceRetrievalException {
        Stock stock = stockRepository.findById(investmentId)
                .orElseThrow(() -> new StockPriceRetrievalException("Ação não encontrada com ID: " + investmentId));

        String ticker = stock.getTicker();
        if (ticker == null || ticker.trim().isEmpty()) {
            log.error("Ticker está vazio para investimento com ID: {}", investmentId);
            throw new StockPriceRetrievalException("Ticker não pode ser vazio.");
        }

        try {
            double currentPrice = stockService.getStockPriceInEUR(ticker);
            stock.setLastSyncedPrice(currentPrice);
            stockRepository.save(stock);

            log.info("Preço atualizado para Ação '{}' (ID: {}): {} EUR", ticker, investmentId, currentPrice);
            return currentPrice;
        } catch (StockPriceRetrievalException e) {
            log.error("Erro ao atualizar o preço para Ação '{}' (ID: {}): {}", ticker, investmentId, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao atualizar o preço para Ação '{}' (ID: {}): {}", ticker, investmentId, e.getMessage());
            throw new StockPriceRetrievalException("Erro ao atualizar o preço da Ação: " + ticker, e);
        }
    }

    /**
     * Atualiza os preços de todas as ações no portfólio do usuário.
     *
     * @param portfolioId ID do portfólio do usuário.
     * @throws StockPriceRetrievalException Se ocorrer um erro ao atualizar os preços.
     */
    @Override
    public void updateAllPrices(UUID portfolioId) throws StockPriceRetrievalException {
        List<Stock> stocks = stockRepository.findByPortfolioId(portfolioId);
        for (Stock stock : stocks) {
            try {
                updatePrice(stock.getId());
            } catch (StockPriceRetrievalException e) {
                log.error("Erro ao atualizar preço para Ação ID: {}", stock.getId(), e);
                // Dependendo da necessidade, pode optar por continuar ou interromper a atualização
                throw e;
            }
        }
        log.info("Todos os preços das ações no portfólio ID {} foram atualizados.", portfolioId);
    }

    /**
     * Calcula o valor atual de uma ação no investimento.
     *
     * @param investmentId ID do investimento em ação.
     * @return Valor atual em EUR.
     * @throws InvestmentException Se ocorrer um erro ao recuperar o valor.
     */
    @Override
    public double getCurrentValue(UUID investmentId) throws InvestmentException {
        Stock stock = stockRepository.findById(investmentId)
                .orElseThrow(() -> new InvestmentException("Ação não encontrada com ID: " + investmentId));

        double currentPrice = stock.getLastSyncedPrice();
        double units = stock.getUnits();
        double currentValue = currentPrice * units;
        stock.setCurrentValue(currentValue);
        stockRepository.save(stock);

        log.info("Valor atual para Ação '{}' (ID: {}): {} EUR", stock.getTicker(), investmentId, currentValue);
        return currentValue;
    }

    /**
     * Recupera todas as ações associadas a um usuário.
     *
     * @param userId ID do usuário.
     * @return Lista de ações.
     */
    @Override
    public List<Stock> getAllStocksByUser(UUID userId) {
        List<Stock> stocks = stockRepository.findByPortfolioUserId(userId);
        log.info("Encontradas {} ações para o usuário ID: {}", stocks.size(), userId);
        return stocks;
    }

    /**
     * Adiciona uma nova ação ao portfólio e atualiza seu valor atual via API.
     *
     * @param stockRequestDTO DTO contendo os dados da ação.
     * @return Ação adicionada com valor atualizado.
     * @throws StockPriceRetrievalException Se ocorrer um erro ao atualizar o valor.
     * @throws InvestmentException Se ocorrer um erro relacionado ao investimento.
     */
    @Override
    public Stock addStock(StockRequestDTO stockRequestDTO) throws StockPriceRetrievalException, InvestmentException {
        // Verifica se o portfólio existe
        UUID portfolioId = stockRequestDTO.getPortfolioId();
        if (!portfolioService.existsById(portfolioId)) {
            log.error("Portfólio não encontrado com ID: {}", portfolioId);
            throw new InvestmentException("Portfólio não encontrado com ID: " + portfolioId);
        }

        // Mapeia DTO para entidade
        Stock stock = stockMapper.toEntity(stockRequestDTO);
        stock.setPortfolio(portfolioService.getPortfolioById(portfolioId));

        // Inicializa valores
        stock.setCurrentValue(0.0);
        stock.setLastSyncedPrice(0.0);

        // Atualiza o valor atual via API antes de salvar
        stockService.updateStockValueFromApi(stock);

        // Salva a ação
        Stock savedStock = stockRepository.save(stock);
        log.info("Nova ação adicionada: '{}', ID: {}", savedStock.getTicker(), savedStock.getId());

        return savedStock;
    }

    /**
     * Remove uma ação específica do portfólio.
     *
     * @param investmentId ID da ação a ser removida.
     * @throws StockPriceRetrievalException Se ocorrer um erro ao remover a ação.
     */
    @Override
    public void removeStock(UUID investmentId) throws StockPriceRetrievalException {
        Stock stock = stockRepository.findById(investmentId)
                .orElseThrow(() -> new StockPriceRetrievalException("Ação não encontrada com ID: " + investmentId));

        try {
            stockRepository.delete(stock);
            log.info("Ação removida: '{}', ID: {}", stock.getTicker(), investmentId);
        } catch (Exception e) {
            log.error("Erro ao remover ação '{}' (ID: {}): {}", stock.getTicker(), investmentId, e.getMessage());
            throw new StockPriceRetrievalException("Erro ao remover a ação: " + stock.getTicker(), e);
        }
    }
}
