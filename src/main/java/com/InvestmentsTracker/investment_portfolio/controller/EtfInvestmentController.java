package com.InvestmentsTracker.investment_portfolio.controller;

import com.InvestmentsTracker.investment_portfolio.exception.EtfPriceRetrievalException;
import com.InvestmentsTracker.investment_portfolio.model.Etf;
import com.InvestmentsTracker.investment_portfolio.service.etf.EtfInvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gerenciar investimentos em ETFs.
 */
@RestController
@RequestMapping("/api/investments/etf")
public class EtfInvestmentController {

    private final EtfInvestmentService etfInvestmentService;

    @Autowired
    public EtfInvestmentController(EtfInvestmentService etfInvestmentService) {
        this.etfInvestmentService = etfInvestmentService;
    }

    /**
     * Endpoint para atualizar manualmente o valor de um ETF específico.
     *
     * @param investmentId ID do investimento em ETF.
     * @param newValue Novo valor atual em EUR.
     * @return Mensagem de sucesso ou erro.
     */
    @PutMapping("/{investmentId}/update-value")
    public ResponseEntity<String> updateEtfValue(
            @PathVariable Long investmentId,
            @RequestParam double newValue) {
        try {
            etfInvestmentService.updateValue(investmentId, newValue);
            return ResponseEntity.ok("Valor atualizado com sucesso.");
        } catch (EtfPriceRetrievalException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint para atualizar manualmente o valor de todos os ETFs em um portfólio.
     *
     * @param portfolioId ID do portfólio do usuário.
     * @param newValue Novo valor atual a ser definido para cada ETF.
     * @return Mensagem de sucesso ou erro.
     */
    @PutMapping("/portfolio/{portfolioId}/update-all-values")
    public ResponseEntity<String> updateAllEtfValues(
            @PathVariable Long portfolioId,
            @RequestParam double newValue) {
        try {
            etfInvestmentService.updateAllValues(portfolioId, newValue);
            return ResponseEntity.ok("Todos os valores dos ETFs foram atualizados com sucesso.");
        } catch (EtfPriceRetrievalException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint para obter o valor atual de um ETF específico.
     *
     * @param investmentId ID do investimento em ETF.
     * @return Valor atual em EUR ou erro.
     */
    @GetMapping("/{investmentId}/current-value")
    public ResponseEntity<Double> getCurrentValue(@PathVariable Long investmentId) {
        try {
            double currentValue = etfInvestmentService.getCurrentValue(investmentId);
            return ResponseEntity.ok(currentValue);
        } catch (EtfPriceRetrievalException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Endpoint para obter todas as ETFs de um usuário específico.
     *
     * @param userId ID do usuário.
     * @return Lista de ETFs ou erro.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Etf>> getAllEtfsByUser(@PathVariable Long userId) {
        List<Etf> etfList = etfInvestmentService.getAllEtfsByUser(userId);
        return ResponseEntity.ok(etfList);
    }

    /**
     * Endpoint para adicionar um novo ETF.
     *
     * @param etf Objeto Etf a ser adicionado.
     * @return Etf criado ou erro.
     */
    @PostMapping("/add")
    public ResponseEntity<Etf> addEtf(@RequestBody Etf etf) {
        Etf savedEtf = etfInvestmentService.addEtf(etf);
        return ResponseEntity.ok(savedEtf);
    }

    /**
     * Endpoint para remover um ETF específico.
     *
     * @param investmentId ID do investimento em ETF.
     * @return Mensagem de sucesso ou erro.
     */
    @DeleteMapping("/{investmentId}/remove")
    public ResponseEntity<String> removeEtf(@PathVariable Long investmentId) {
        try {
            etfInvestmentService.removeEtf(investmentId);
            return ResponseEntity.ok("ETF removido com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao remover ETF.");
        }
    }
}
