package com.InvestmentsTracker.investment_portfolio.controller;

import com.InvestmentsTracker.investment_portfolio.exception.StockPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.model.Stock;
import com.InvestmentsTracker.investment_portfolio.service.stock.StockInvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gerenciar investimentos em Stocks.
 */
@RestController
@RequestMapping("/api/investments/stock")
public class StockInvestmentController {

    private final StockInvestmentService stockInvestmentService;

    @Autowired
    public StockInvestmentController(StockInvestmentService stockInvestmentService) {
        this.stockInvestmentService = stockInvestmentService;
    }

    /**
     * Endpoint para atualizar automaticamente o valor de um Stock específico via API.
     *
     * @param investmentId ID do investimento em Stock.
     * @return Mensagem de sucesso ou erro.
     */
    @PutMapping("/{investmentId}/update-value")
    public ResponseEntity<String> updateStockValue(@PathVariable Long investmentId) {
        try {
            stockInvestmentService.updateValue(investmentId);
            return ResponseEntity.ok("Valor atualizado com sucesso.");
        } catch (StockPriceRetrievalException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint para atualizar automaticamente o valor de todos os Stocks em um portfólio via API.
     *
     * @param portfolioId ID do portfólio do usuário.
     * @return Mensagem de sucesso ou erro.
     */
    @PutMapping("/portfolio/{portfolioId}/update-all-values")
    public ResponseEntity<String> updateAllStockValues(@PathVariable Long portfolioId) {
        try {
            stockInvestmentService.updateAllValues(portfolioId);
            return ResponseEntity.ok("Todos os valores dos Stocks foram atualizados com sucesso.");
        } catch (StockPriceRetrievalException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint para obter o valor atual de um Stock específico.
     *
     * @param investmentId ID do investimento em Stock.
     * @return Valor atual em EUR ou erro.
     */
    @GetMapping("/{investmentId}/current-value")
    public ResponseEntity<Double> getCurrentValue(@PathVariable Long investmentId) {
        try {
            double currentValue = stockInvestmentService.getCurrentValue(investmentId);
            return ResponseEntity.ok(currentValue);
        } catch (StockPriceRetrievalException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Endpoint para obter todos os Stocks de um usuário específico.
     *
     * @param userId ID do usuário.
     * @return Lista de Stocks ou erro.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Stock>> getAllStocksByUser(@PathVariable Long userId) {
        List<Stock> stockList = stockInvestmentService.getAllStocksByUser(userId);
        return ResponseEntity.ok(stockList);
    }

    /**
     * Endpoint para adicionar um novo Stock. O valor atual será atualizado automaticamente via API.
     *
     * @param stock Objeto Stock a ser adicionado.
     * @return Stock criado ou erro.
     */
    @PostMapping("/add")
    public ResponseEntity<?> addStock(@RequestBody Stock stock) {
        // Validações básicas
        if (stock.getTicker() == null || stock.getTicker().isEmpty()) {
            return ResponseEntity.badRequest().body("O ticker da ação é obrigatório.");
        }
        if (stock.getBuyPrice() <= 0) {
            return ResponseEntity.badRequest().body("O preço de compra deve ser maior que zero.");
        }
        if (stock.getUnits() <= 0) {
            return ResponseEntity.badRequest().body("O número de unidades deve ser maior que zero.");
        }
        try {
            Stock savedStock = stockInvestmentService.addStock(stock);
            return ResponseEntity.ok(savedStock);
        } catch (StockPriceRetrievalException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao adicionar Stock.");
        }
    }

    /**
     * Endpoint para remover um Stock específico.
     *
     * @param investmentId ID do investimento em Stock.
     * @return Mensagem de sucesso ou erro.
     */
    @DeleteMapping("/{investmentId}/remove")
    public ResponseEntity<String> removeStock(@PathVariable Long investmentId) {
        try {
            stockInvestmentService.removeStock(investmentId);
            return ResponseEntity.ok("Stock removido com sucesso.");
        } catch (StockPriceRetrievalException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao remover Stock.");
        }
    }
}
