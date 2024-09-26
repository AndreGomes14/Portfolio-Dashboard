package com.InvestmentsTracker.investment_portfolio.service.user;

import com.InvestmentsTracker.investment_portfolio.dto.AuthResponse;
import com.InvestmentsTracker.investment_portfolio.dto.LoginRequest;
import com.InvestmentsTracker.investment_portfolio.dto.RegisterRequest;
import com.InvestmentsTracker.investment_portfolio.exception.PortfolioNotFoundException;
import com.InvestmentsTracker.investment_portfolio.exception.UserNotFoundException;
import com.InvestmentsTracker.investment_portfolio.model.Investment;
import com.InvestmentsTracker.investment_portfolio.model.Portfolio;

import java.util.List;
import java.util.Optional;

/**
 * Interface para serviços de domínio que gerenciam operações relacionadas a Users, Portfolios e Investments.
 */
public interface UserService {

    /**
     * Registra um novo usuário.
     *
     * @param registerRequest Dados de registro do usuário.
     * @return AuthResponse contendo o token JWT.
     */
    AuthResponse registerUser(RegisterRequest registerRequest);

    /**
     * Autentica um usuário e retorna um token JWT.
     *
     * @param loginRequest Dados de login do usuário.
     * @return AuthResponse contendo o token JWT.
     */
    AuthResponse authenticateUser(LoginRequest loginRequest);

    /**
     * Exclui um usuário existente.
     *
     * @param userId ID do usuário a ser excluído.
     * @throws UserNotFoundException Se o usuário não for encontrado.
     */
    void deleteUser(Long userId) throws UserNotFoundException;

    /**
     * Cria um novo portfólio para um usuário específico.
     *
     * @param userId ID do usuário.
     * @param portfolio Instância de Portfolio a ser criada.
     * @return Portfolio criado.
     * @throws UserNotFoundException Se o usuário não for encontrado.
     */
    Portfolio createPortfolio(Long userId, Portfolio portfolio) throws UserNotFoundException;

    /**
     * Exclui um portfólio específico de um usuário.
     *
     * @param userId ID do usuário.
     * @param portfolioId ID do portfólio a ser excluído.
     * @throws UserNotFoundException Se o usuário não for encontrado.
     * @throws PortfolioNotFoundException Se o portfólio não for encontrado.
     */
    void deletePortfolio(Long userId, Long portfolioId) throws UserNotFoundException, PortfolioNotFoundException;

    /**
     * Adiciona um investimento a um portfólio específico de um usuário.
     *
     * @param userId ID do usuário.
     * @param portfolioId ID do portfólio.
     * @param investment Instância de Investment a ser adicionada.
     * @return Investment adicionado.
     * @throws UserNotFoundException Se o usuário não for encontrado.
     * @throws PortfolioNotFoundException Se o portfólio não for encontrado.
     */
    Investment addInvestmentToPortfolio(Long userId, Long portfolioId, Investment investment) throws UserNotFoundException, PortfolioNotFoundException;

    /**
     * Atualiza um investimento existente em um portfólio de um usuário.
     *
     * @param userId ID do usuário.
     * @param portfolioId ID do portfólio.
     * @param investment Instância de Investment com os dados atualizados.
     * @return Investment atualizado.
     * @throws UserNotFoundException Se o usuário não for encontrado.
     * @throws PortfolioNotFoundException Se o portfólio não for encontrado.
     */
    Investment updateInvestment(Long userId, Long portfolioId, Investment investment) throws UserNotFoundException, PortfolioNotFoundException;

    /**
     * Remove um investimento específico de um portfólio de um usuário.
     *
     * @param userId ID do usuário.
     * @param portfolioId ID do portfólio.
     * @param investmentId ID do investimento a ser removido.
     * @throws UserNotFoundException Se o usuário não for encontrado.
     * @throws PortfolioNotFoundException Se o portfólio não for encontrado.
     */
    void removeInvestment(Long userId, Long portfolioId, Long investmentId) throws UserNotFoundException, PortfolioNotFoundException;

    /**
     * Recupera todos os portfólios de um usuário.
     *
     * @param userId ID do usuário.
     * @return Lista de portfólios.
     * @throws UserNotFoundException Se o usuário não for encontrado.
     */
    Portfolio getPortfolioByUser(Long userId) throws UserNotFoundException;

    /**
     * Recupera todos os investimentos de um portfólio específico de um usuário.
     *
     * @param userId ID do usuário.
     * @param portfolioId ID do portfólio.
     * @return Lista de investimentos.
     * @throws UserNotFoundException Se o usuário não for encontrado.
     * @throws PortfolioNotFoundException Se o portfólio não for encontrado.
     */
    List<Investment> getAllInvestmentsByPortfolio(Long userId, Long portfolioId) throws UserNotFoundException, PortfolioNotFoundException;
}
