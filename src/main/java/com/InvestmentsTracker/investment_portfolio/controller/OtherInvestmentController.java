package com.InvestmentsTracker.investment_portfolio.controller;

import com.InvestmentsTracker.investment_portfolio.exception.OtherPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.model.Other;
import com.InvestmentsTracker.investment_portfolio.service.other.OtherInvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gerenciar investimentos em "Other".
 */
@RestController
@RequestMapping("/api/investments/other")
public class OtherInvestmentController {

    private final OtherInvestmentService otherInvestmentService;

    @Autowired
    public OtherInvestmentController(OtherInvestmentService otherInvestmentService) {
        this.otherInvestmentService = otherInvestmentService;
    }

    /**
     * Endpoint para atualizar manualmente o valor de um investimento "Other" específico.
     *
     * @param investmentId ID do investimento em "Other".
     * @param newValue Novo valor atual em EUR.
     * @return Mensagem de sucesso ou erro.
     */
    @PutMapping("/{investmentId}/update-value")
    public ResponseEntity<String> updateOtherValue(
            @PathVariable Long investmentId,
            @RequestParam double newValue) {
        try {
            otherInvestmentService.updateValue(investmentId, newValue);
            return ResponseEntity.ok("Valor atualizado com sucesso.");
        } catch (OtherPriceRetrievalException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint para atualizar manualmente o valor de todos os investimentos "Other" em um portfólio.
     *
     * @param portfolioId ID do portfólio do usuário.
     * @param newValue Novo valor atual a ser definido para cada "Other".
     * @return Mensagem de sucesso ou erro.
     */
    @PutMapping("/portfolio/{portfolioId}/update-all-values")
    public ResponseEntity<String> updateAllOtherValues(
            @PathVariable Long portfolioId,
            @RequestParam double newValue) {
        try {
            otherInvestmentService.updateAllValues(portfolioId, newValue);
            return ResponseEntity.ok("Todos os valores dos investimentos 'Other' foram atualizados com sucesso.");
        } catch (OtherPriceRetrievalException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint para obter o valor atual de um investimento "Other" específico.
     *
     * @param investmentId ID do investimento em "Other".
     * @return Valor atual em EUR ou erro.
     */
    @GetMapping("/{investmentId}/current-value")
    public ResponseEntity<Double> getCurrentValue(@PathVariable Long investmentId) {
        try {
            double currentValue = otherInvestmentService.getCurrentValue(investmentId);
            return ResponseEntity.ok(currentValue);
        } catch (OtherPriceRetrievalException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Endpoint para obter todos os investimentos "Other" de um usuário específico.
     *
     * @param userId ID do usuário.
     * @return Lista de investimentos "Other" ou erro.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Other>> getAllOthersByUser(@PathVariable Long userId) {
        List<Other> otherList = otherInvestmentService.getAllOthersByUser(userId);
        return ResponseEntity.ok(otherList);
    }

    /**
     * Endpoint para adicionar um novo investimento "Other".
     *
     * @param other Objeto Other a ser adicionado.
     * @return Other criado ou erro.
     */
    @PostMapping("/add")
    public ResponseEntity<Other> addOther(@RequestBody Other other) {
        try {
            Other savedOther = otherInvestmentService.addOther(other);
            return ResponseEntity.ok(savedOther);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Endpoint para remover um investimento "Other" específico.
     *
     * @param investmentId ID do investimento em "Other".
     * @return Mensagem de sucesso ou erro.
     */
    @DeleteMapping("/{investmentId}/remove")
    public ResponseEntity<String> removeOther(@PathVariable Long investmentId) {
        try {
            otherInvestmentService.removeOther(investmentId);
            return ResponseEntity.ok("Investimento 'Other' removido com sucesso.");
        } catch (OtherPriceRetrievalException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao remover investimento 'Other'.");
        }
    }
}
