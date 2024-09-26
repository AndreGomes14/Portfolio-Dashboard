package com.InvestmentsTracker.investment_portfolio.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para requisição de registro de usuário.
 */
@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "O username é obrigatório.")
    private String username;

    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "O email deve ser válido.")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    private String password;
}
