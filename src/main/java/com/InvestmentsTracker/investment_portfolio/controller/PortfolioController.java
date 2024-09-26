package com.InvestmentsTracker.investment_portfolio.controller;

import com.InvestmentsTracker.investment_portfolio.dto.PortfolioDTO;
import com.InvestmentsTracker.investment_portfolio.exception.InvalidInputException;
import com.InvestmentsTracker.investment_portfolio.exception.ResourceNotFoundException;
import com.InvestmentsTracker.investment_portfolio.service.portfolio.PortfolioService;
import com.InvestmentsTracker.investment_portfolio.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {
    private static final Logger logger = LoggerFactory.getLogger(PortfolioController.class);

    @Autowired
    private PortfolioService portfolioService;

    // Get the portfolio for the authenticated user
    @GetMapping
    public ResponseEntity<?> getPortfolio(@AuthenticationPrincipal User user) {
        try {
            Long userId = user.getId();
            logger.info("Solicitação para obter o portfólio do usuário com ID: {}", userId);
            PortfolioDTO portfolioDTO = portfolioService.getPortfolioByUserId(userId);
            logger.debug("Portfólio encontrado: {}", portfolioDTO);
            return ResponseEntity.ok(portfolioDTO);
        } catch (ResourceNotFoundException e) {
            logger.warn("Portfólio não encontrado para o usuário com ID: {}", user.getId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Erro inesperado ao obter o portfólio para o usuário com ID: {}", user.getId(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao obter o portfólio.");
        }
    }

    @PostMapping
    public ResponseEntity<?> createPortfolio(@AuthenticationPrincipal User user,
                                             @RequestBody PortfolioDTO portfolioDTO) {
        try {
            Long userId = user.getId();
            logger.info("Solicitação para criar um novo portfólio para o usuário com ID: {}", userId);
            PortfolioDTO createdPortfolio = portfolioService.createPortfolio(userId, portfolioDTO);
            logger.debug("Portfólio criado: {}", createdPortfolio);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPortfolio);
        } catch (InvalidInputException e) {
            logger.warn("Entrada inválida ao criar portfólio para o usuário com ID: {}. Erro: {}", user.getId(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Erro inesperado ao criar portfólio para o usuário com ID: {}", user.getId(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao criar o portfólio.");
        }
    }

}
