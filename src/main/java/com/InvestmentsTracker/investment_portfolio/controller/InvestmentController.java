package com.InvestmentsTracker.investment_portfolio.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Classe base abstrata para controladores de investimentos.
 */
public abstract class InvestmentController {

    /**
     * Remove um investimento específico.
     *
     * @param investmentId ID do investimento a ser removido.
     * @return Resposta sem conteúdo.
     */
    @DeleteMapping("/{investmentId}")
    public abstract ResponseEntity<Void> removeInvestment(@PathVariable UUID investmentId);

    /**
     * Recupera o valor atual de um investimento específico.
     *
     * @param investmentId ID do investimento.
     * @return Valor atual em EUR.
     */
    @GetMapping("/{investmentId}/current-value")
    public abstract ResponseEntity<Double> getCurrentValue(@PathVariable UUID investmentId);
}
