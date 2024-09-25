package com.InvestmentsTracker.investment_portfolio.controller;

import com.InvestmentsTracker.investment_portfolio.dto.PortfolioDTO;
import com.InvestmentsTracker.investment_portfolio.service.PortfolioService;
import com.InvestmentsTracker.investment_portfolio.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    // Get the portfolio for the authenticated user
    @GetMapping
    public ResponseEntity<PortfolioDTO> getPortfolio(@AuthenticationPrincipal User user) {
        PortfolioDTO portfolioDTO = portfolioService.getPortfolioByUserId(user.getId());
        return ResponseEntity.ok(portfolioDTO);
    }

    // Additional endpoints as needed
}
