// src/main/java/com/InvestmentsTracker/investment_portfolio/controller/InvestmentController.java
package com.InvestmentsTracker.investment_portfolio.controller;

import com.InvestmentsTracker.investment_portfolio.model.Investment;
import com.InvestmentsTracker.investment_portfolio.service.InvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador para gerenciar operações gerais de Investimentos.
 */
@RestController
@RequestMapping("/api/investments")
public class InvestmentController {

    private final InvestmentService investmentService;

    @Autowired
    public InvestmentController(InvestmentService investmentService) {
        this.investmentService = investmentService;
    }

    /**
     * Endpoint para adicionar um novo investimento.
     *
     * @param investment Objeto Investment a ser adicionado.
     * @return Investment criado ou mensagem de erro.
     */
    @PostMapping("/add")
    public ResponseEntity<?> addInvestment(@RequestBody Investment investment) {
        // Validações básicas podem ser adicionadas aqui
        try {
            Investment savedInvestment = investmentService.addInvestment(investment);
            return ResponseEntity.ok(savedInvestment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao adicionar investimento.");
        }
    }

    /**
     * Endpoint para atualizar um investimento existente.
     *
     * @param investmentId ID do investimento a ser atualizado.
     * @param investment Objeto Investment com os dados atualizados.
     * @return Investment atualizado ou mensagem de erro.
     */
    @PutMapping("/{investmentId}/update")
    public ResponseEntity<?> updateInvestment(
            @PathVariable Long investmentId,
            @RequestBody Investment investment) {
        if (!investmentId.equals(investment.getId())) {
            return ResponseEntity.badRequest().body("ID do caminho não corresponde ao ID do corpo.");
        }
        try {
            Investment updatedInvestment = investmentService.updateInvestment(investment);
            return ResponseEntity.ok(updatedInvestment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar investimento.");
        }
    }

    /**
     * Endpoint para remover um investimento específico.
     *
     * @param investmentId ID do investimento a ser removido.
     * @return Mensagem de sucesso ou erro.
     */
    @DeleteMapping("/{investmentId}/remove")
    public ResponseEntity<String> removeInvestment(@PathVariable Long investmentId) {
        try {
            investmentService.removeInvestment(investmentId);
            return ResponseEntity.ok("Investimento removido com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao remover investimento.");
        }
    }

    /**
     * Endpoint para recuperar um investimento por ID.
     *
     * @param investmentId ID do investimento.
     * @return Investment encontrado ou mensagem de erro.
     */
    @GetMapping("/{investmentId}")
    public ResponseEntity<?> getInvestmentById(@PathVariable Long investmentId) {
        try {
            Investment investment = investmentService.getInvestmentById(investmentId);
            return ResponseEntity.ok(investment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint para recuperar todos os investimentos de um portfólio específico.
     *
     * @param portfolioId ID do portfólio.
     * @return Lista de investimentos ou mensagem de erro.
     */
    @GetMapping("/portfolio/{portfolioId}")
    public ResponseEntity<?> getAllInvestmentsByPortfolio(@PathVariable Long portfolioId) {
        try {
            List<Investment> investments = investmentService.getAllInvestmentsByPortfolio(portfolioId);
            return ResponseEntity.ok(investments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao recuperar investimentos.");
        }
    }

    /**
     * Endpoint para recuperar todos os investimentos de um usuário específico.
     *
     * @param userId ID do usuário.
     * @return Lista de investimentos ou mensagem de erro.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllInvestmentsByUser(@PathVariable Long userId) {
        try {
            List<Investment> investments = investmentService.getAllInvestmentsByUser(userId);
            return ResponseEntity.ok(investments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao recuperar investimentos.");
        }
    }
}
