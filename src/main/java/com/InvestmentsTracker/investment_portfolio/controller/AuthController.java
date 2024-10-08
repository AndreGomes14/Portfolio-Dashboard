package com.InvestmentsTracker.investment_portfolio.controller;

import com.InvestmentsTracker.investment_portfolio.dto.auth.AuthResponse;
import com.InvestmentsTracker.investment_portfolio.dto.auth.LoginRequest;
import com.InvestmentsTracker.investment_portfolio.dto.auth.RegisterRequest;
import com.InvestmentsTracker.investment_portfolio.exception.EmailAlreadyExistsException;
import com.InvestmentsTracker.investment_portfolio.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para gerenciar operações de autenticação: Registro, Login e Logout.
 */
@RestController
@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register"; // Thymeleaf template: register.html
    }
    /**
     * Processa o registro do usuário.
     */
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("registerRequest") RegisterRequest registerRequest,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        try {
            userService.registerUser(registerRequest);
            model.addAttribute("successMessage", "Registro realizado com sucesso. Por favor, faça o login.");
            return "login"; // Redireciona para a página de login
        } catch (EmailAlreadyExistsException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "register";
        } catch (Exception ex) {
            model.addAttribute("errorMessage", "Erro ao registrar usuário.");
            return "register";
        }
    }
    @GetMapping("/login")
    public String showLoginForm(Model model,
                                @RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout) {
        if (error != null) {
            model.addAttribute("errorMessage", "Credenciais inválidas.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "Logout realizado com sucesso.");
        }
        model.addAttribute("loginRequest", new LoginRequest());
        return "login"; // Thymeleaf template: login.html
    }

    /**
     * Endpoint para autenticar um usuário e gerar um token JWT.
     *
     * @param loginRequest Dados de login do usuário.
     * @return AuthResponse contendo o token JWT ou mensagem de erro.
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse authResponse = userService.authenticateUser(loginRequest);
            // Retorna o token JWT para o frontend armazenar (e.g., no localStorage) e redirecionar para o dashboard
            return ResponseEntity.ok(authResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao autenticar usuário.");
        }
    }

    /**
     * Endpoint para logout do usuário.
     *
     * **Nota:** Com JWT, o logout geralmente é gerenciado no frontend descartando o token.
     * Se implementar blacklist de tokens, você pode adicionar a lógica aqui.
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        // Implementação de logout dependerá da estratégia de gerenciamento de tokens.
        // Com JWT, geralmente o logout é feito no frontend removendo o token.
        // Se implementar blacklist, adicione o token atual à lista de tokens inválidos aqui.
        return ResponseEntity.ok("Logout realizado com sucesso.");
    }
}
