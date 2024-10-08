package com.InvestmentsTracker.investment_portfolio.dto.auth;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO para resposta de autenticação contendo o token JWT.
 */
@Getter
@Setter
public class AuthResponse {
    private String accessToken;
    private String tokenType;

    /**
     * Construtor padrão que define o tokenType como "Bearer".
     */
    public AuthResponse() {
        this.tokenType = "Bearer";
    }

    /**
     * Construtor que aceita apenas o accessToken e define o tokenType como "Bearer".
     *
     * @param accessToken O token de acesso JWT.
     */
    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
        this.tokenType = "Bearer";
    }

    /**
     * Construtor que aceita tanto o accessToken quanto o tokenType.
     *
     * @param accessToken O token de acesso JWT.
     * @param tokenType   O tipo do token, por exemplo, "Bearer".
     */
    public AuthResponse(String accessToken, String tokenType) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }
}
