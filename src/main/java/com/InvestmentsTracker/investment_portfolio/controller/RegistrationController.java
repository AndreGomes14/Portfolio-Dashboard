// src/main/java/com/InvestmentsTracker/investment_portfolio/controller/RegistrationController.java
package com.InvestmentsTracker.investment_portfolio.controller;

import com.InvestmentsTracker.investment_portfolio.dto.auth.RegisterRequest;
import com.InvestmentsTracker.investment_portfolio.exception.DuplicateUserException;
import com.InvestmentsTracker.investment_portfolio.exception.InvalidInputException;
import com.InvestmentsTracker.investment_portfolio.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private final UserService userService;

    // Exibir o formulário de registro
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }

    // Processar a solicitação de registro
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("registerRequest") RegisterRequest registerRequest,
                               BindingResult bindingResult,
                               Model model) {
        logger.info("Processando solicitação de registro para o usuário: {}", registerRequest.getUsername());

        // Verificar erros de validação
        if (bindingResult.hasErrors()) {
            logger.warn("Erros de validação encontrados durante o registro do usuário: {}", registerRequest.getUsername());
            return "register";
        }

        try {
            // Chamar o serviço para registrar o usuário
            userService.registerUser(registerRequest);
            logger.info("Usuário registrado com sucesso: {}", registerRequest.getUsername());
            return "redirect:/login?registered";
        } catch (DuplicateUserException e) {
            logger.warn("Tentativa de registro de usuário duplicado: {}", registerRequest.getUsername());
            model.addAttribute("registrationError", "Nome de usuário ou e-mail já está em uso.");
            return "register";
        } catch (InvalidInputException e) {
            logger.warn("Entrada inválida durante o registro do usuário: {}. Erro: {}", registerRequest.getUsername(), e.getMessage());
            model.addAttribute("registrationError", e.getMessage());
            return "register";
        } catch (Exception e) {
            logger.error("Erro inesperado durante o registro do usuário: {}", registerRequest.getUsername(), e);
            model.addAttribute("registrationError", "Ocorreu um erro ao registrar o usuário. Por favor, tente novamente mais tarde.");
            return "register";
        }
    }
}
