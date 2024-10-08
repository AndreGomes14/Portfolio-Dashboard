package com.InvestmentsTracker.investment_portfolio.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para requisição de login de usuário.
 */
@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "O email deve ser válido.")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    private String password;
}
