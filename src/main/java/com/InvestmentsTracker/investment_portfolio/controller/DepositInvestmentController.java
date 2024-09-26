package com.InvestmentsTracker.investment_portfolio.controller;

import com.InvestmentsTracker.investment_portfolio.exception.DepositPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.model.Savings;
import com.InvestmentsTracker.investment_portfolio.service.savings.SavingsInvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gerenciar investimentos em Savings (Depósitos).
 */
@RestController
@RequestMapping("/api/investments/deposit")
public class DepositInvestmentController {

    private final SavingsInvestmentService depositInvestmentService;

    @Autowired
    public DepositInvestmentController(SavingsInvestmentService depositInvestmentService) {
        this.depositInvestmentService = depositInvestmentService;
    }

    /**
     * Endpoint para atualizar manualmente o valor de um Savings específico.
     *
     * @param investmentId ID do investimento em Savings.
     * @param newValue Novo valor atual em EUR.
     * @return Mensagem de sucesso ou erro.
     */
    @PutMapping("/{investmentId}/update-value")
    public ResponseEntity<String> updateSavingsValue(
            @PathVariable Long investmentId,
            @RequestParam double newValue) {
        try {
            depositInvestmentService.updateValue(investmentId, newValue);
            return ResponseEntity.ok("Valor atualizado com sucesso.");
        } catch (DepositPriceRetrievalException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint para atualizar manualmente o valor de todos os Savings em um portfólio.
     *
     * @param portfolioId ID do portfólio do usuário.
     * @param newValue Novo valor atual a ser definido para cada Savings.
     * @return Mensagem de sucesso ou erro.
     */
    @PutMapping("/portfolio/{portfolioId}/update-all-values")
    public ResponseEntity<String> updateAllSavingsValues(
            @PathVariable Long portfolioId,
            @RequestParam double newValue) {
        try {
            depositInvestmentService.updateAllValues(portfolioId, newValue);
            return ResponseEntity.ok("Todos os valores dos Savings foram atualizados com sucesso.");
        } catch (DepositPriceRetrievalException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint para obter o valor atual de um Savings específico.
     *
     * @param investmentId ID do investimento em Savings.
     * @return Valor atual em EUR ou erro.
     */
    @GetMapping("/{investmentId}/current-value")
    public ResponseEntity<Double> getCurrentValue(@PathVariable Long investmentId) {
        try {
            double currentValue = depositInvestmentService.getCurrentValue(investmentId);
            return ResponseEntity.ok(currentValue);
        } catch (DepositPriceRetrievalException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Endpoint para obter todas as Savings de um usuário específico.
     *
     * @param userId ID do usuário.
     * @return Lista de Savings ou erro.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Savings>> getAllSavingsByUser(@PathVariable Long userId) {
        List<Savings> savingsList = depositInvestmentService.getAllDepositsByUser(userId);
        return ResponseEntity.ok(savingsList);
    }

    /**
     * Endpoint para adicionar um novo Savings.
     *
     * @param savings Objeto Savings a ser adicionado.
     * @return Savings criado ou erro.
     */
    @PostMapping("/add")
    public ResponseEntity<Savings> addSavings(@RequestBody Savings savings) {
        Savings savedSavings = depositInvestmentService.addSavings(savings);
        return ResponseEntity.ok(savedSavings);
    }

    /**
     * Endpoint para remover um Savings específico.
     *
     * @param investmentId ID do investimento em Savings.
     * @return Mensagem de sucesso ou erro.
     */
    @DeleteMapping("/{investmentId}/remove")
    public ResponseEntity<String> removeSavings(@PathVariable Long investmentId) {
        try {
            depositInvestmentService.removeSavings(investmentId);
            return ResponseEntity.ok("Savings removido com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao remover Savings.");
        }
    }
}
