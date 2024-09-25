package com.InvestmentsTracker.investment_portfolio.controller;

import com.InvestmentsTracker.investment_portfolio.dto.InvestmentDTO;
import com.InvestmentsTracker.investment_portfolio.model.User;
import com.InvestmentsTracker.investment_portfolio.service.InvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/investments")
public class InvestmentController {

    @Autowired
    private InvestmentService investmentService;

    // Get investments for the authenticated user's portfolio
    @GetMapping
    public ResponseEntity<List<InvestmentDTO>> getInvestments(@AuthenticationPrincipal User user) {
        Long portfolioId = user.getPortfolio().getId();
        List<InvestmentDTO> investments = investmentService.getInvestmentsByPortfolioId(portfolioId);
        return ResponseEntity.ok(investments);
    }

    // Get a specific investment by ID
    @GetMapping("/{id}")
    public ResponseEntity<InvestmentDTO> getInvestment(@PathVariable Long id) {
        InvestmentDTO investment = investmentService.getInvestmentById(id);
        return ResponseEntity.ok(investment);
    }

    // Create a new investment
    @PostMapping
    public ResponseEntity<InvestmentDTO> createInvestment(@AuthenticationPrincipal User user,
                                                          @RequestBody InvestmentDTO investmentDTO) {
        Long portfolioId = user.getPortfolio().getId();
        InvestmentDTO createdInvestment = investmentService.createInvestment(portfolioId, investmentDTO);
        return ResponseEntity.ok(createdInvestment);
    }

    // Update an existing investment
    @PutMapping("/{id}")
    public ResponseEntity<InvestmentDTO> updateInvestment(@PathVariable Long id,
                                                          @RequestBody InvestmentDTO investmentDTO) {
        InvestmentDTO updatedInvestment = investmentService.updateInvestment(id, investmentDTO);
        return ResponseEntity.ok(updatedInvestment);
    }

    // Delete an investment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvestment(@PathVariable Long id) {
        investmentService.deleteInvestment(id);
        return ResponseEntity.noContent().build();
    }
}
