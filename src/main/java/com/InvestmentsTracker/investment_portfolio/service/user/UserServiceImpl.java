package com.InvestmentsTracker.investment_portfolio.service.user;

import com.InvestmentsTracker.investment_portfolio.dto.AuthResponse;
import com.InvestmentsTracker.investment_portfolio.dto.LoginRequest;
import com.InvestmentsTracker.investment_portfolio.dto.RegisterRequest;
import com.InvestmentsTracker.investment_portfolio.exception.EmailAlreadyExistsException;
import com.InvestmentsTracker.investment_portfolio.exception.InvalidCredentialsException;
import com.InvestmentsTracker.investment_portfolio.exception.PortfolioNotFoundException;
import com.InvestmentsTracker.investment_portfolio.exception.UserNotFoundException;
import com.InvestmentsTracker.investment_portfolio.model.Investment;
import com.InvestmentsTracker.investment_portfolio.model.Portfolio;
import com.InvestmentsTracker.investment_portfolio.model.User;
import com.InvestmentsTracker.investment_portfolio.repository.PortfolioRepository;
import com.InvestmentsTracker.investment_portfolio.repository.UserRepository;
import com.InvestmentsTracker.investment_portfolio.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementação do UserService que gerencia operações relacionadas a Users, Portfolios e Investments.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse registerUser(RegisterRequest registerRequest) {
        // Verifica se o email já está registrado
        Optional<User> existingUser = userRepository.findByEmail(registerRequest.getEmail());
        if (existingUser.isPresent()) {
            log.warn("Tentativa de registrar com email existente: {}", registerRequest.getEmail());
            throw new EmailAlreadyExistsException("Email já está registrado.");
        }

        // Cria uma nova instância de User
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        // Codifica a senha antes de salvar
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        User savedUser = userRepository.save(user);
        log.info("Novo usuário registrado: {}", savedUser);

        // Retorna uma resposta de autenticação vazia ou personalizada
        return new AuthResponse("");
    }

    @Override
    public AuthResponse authenticateUser(LoginRequest loginRequest) {
        try {
            // Autentica o usuário
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            // Gera o token JWT
            String token = jwtUtil.generateToken(authentication.getName());
            log.info("Usuário autenticado: {}", authentication.getName());
            return new AuthResponse(token);
        } catch (BadCredentialsException ex) {
            log.warn("Tentativa de login falhou para email: {}", loginRequest.getEmail());
            throw new InvalidCredentialsException("Credenciais inválidas.");
        }
    }

    @Override
    public void deleteUser(Long userId) throws UserNotFoundException {
        if (!userRepository.existsById(userId)) {
            log.warn("Tentativa de excluir usuário inexistente: ID {}", userId);
            throw new UserNotFoundException("Usuário não encontrado com ID: " + userId);
        }
        userRepository.deleteById(userId);
        log.info("Usuário excluído: ID {}", userId);
    }

    @Override
    public Portfolio createPortfolio(Long userId, Portfolio portfolio) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("Usuário não encontrado ao criar portfólio: ID {}", userId);
                    return new UserNotFoundException("Usuário não encontrado com ID: " + userId);
                });
        portfolio.setUser(user);
        Portfolio savedPortfolio = portfolioRepository.save(portfolio);
        log.info("Novo portfólio criado para usuário ID {}: {}", userId, savedPortfolio);
        return savedPortfolio;
    }

    @Override
    public void deletePortfolio(Long userId, Long portfolioId) throws UserNotFoundException, PortfolioNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("Usuário não encontrado ao excluir portfólio: ID {}", userId);
                    return new UserNotFoundException("Usuário não encontrado com ID: " + userId);
                });
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> {
                    log.warn("Portfólio não encontrado ao excluir: ID {}", portfolioId);
                    return new PortfolioNotFoundException("Portfólio não encontrado com ID: " + portfolioId);
                });
        if (!portfolio.getUser().getId().equals(userId)) {
            log.warn("Tentativa de excluir portfólio de outro usuário: Portfólio ID {}, User ID {}", portfolioId, userId);
            throw new PortfolioNotFoundException("Portfólio não encontrado para o usuário com ID: " + userId);
        }
        portfolioRepository.delete(portfolio);
        log.info("Portfólio excluído: ID {} do usuário ID {}", portfolioId, userId);
    }

    @Override
    public Investment addInvestmentToPortfolio(Long userId, Long portfolioId, Investment investment) throws UserNotFoundException, PortfolioNotFoundException {
        Portfolio portfolio = getPortfolioByUserAndPortfolioId(userId, portfolioId);
        investment.setPortfolio(portfolio);
        portfolioRepository.save(portfolio).getInvestments().add(investment);
        log.info("Novo investimento adicionado: {} ao portfólio ID {} do usuário ID {}", investment, portfolioId, userId);
        return investment;
    }

    @Override
    public Investment updateInvestment(Long userId, Long portfolioId, Investment investment) throws UserNotFoundException, PortfolioNotFoundException {
        Portfolio portfolio = getPortfolioByUserAndPortfolioId(userId, portfolioId);
        Optional<Investment> existingInvestmentOpt = portfolio.getInvestments().stream()
                .filter(inv -> inv.getId().equals(investment.getId()))
                .findFirst();
        if (existingInvestmentOpt.isEmpty()) {
            log.warn("Investimento não encontrado ao atualizar: ID {} no portfólio ID {}", investment.getId(), portfolioId);
            throw new PortfolioNotFoundException("Investimento não encontrado com ID: " + investment.getId());
        }
        Investment existingInvestment = existingInvestmentOpt.get();
        // Atualiza os campos necessários
        existingInvestment.setBuyPrice(investment.getBuyPrice());
        existingInvestment.setUnits(investment.getUnits());
        // Salva o portfólio para persistir as alterações
        portfolioRepository.save(portfolio);
        log.info("Investimento atualizado: {} no portfólio ID {} do usuário ID {}", existingInvestment, portfolioId, userId);
        return existingInvestment;
    }

    @Override
    public void removeInvestment(Long userId, Long portfolioId, Long investmentId) throws UserNotFoundException, PortfolioNotFoundException {
        Portfolio portfolio = getPortfolioByUserAndPortfolioId(userId, portfolioId);
        Investment investmentToRemove = portfolio.getInvestments().stream()
                .filter(inv -> inv.getId().equals(investmentId))
                .findFirst()
                .orElseThrow(() -> {
                    log.warn("Investimento não encontrado ao remover: ID {}", investmentId);
                    return new PortfolioNotFoundException("Investimento não encontrado com ID: " + investmentId);
                });
        portfolio.getInvestments().remove(investmentToRemove);
        portfolioRepository.save(portfolio);
        log.info("Investimento removido: ID {} do portfólio ID {} do usuário ID {}", investmentId, portfolioId, userId);
    }

    @Override
    public Portfolio getPortfolioByUser(Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("Usuário não encontrado ao recuperar portfólio: ID {}", userId);
                    return new UserNotFoundException("Usuário não encontrado com ID: " + userId);
                });

        // Obtém o portfólio associado ao usuário
        Portfolio portfolio = user.getPortfolio();

        if (portfolio != null) {
            log.info("Portfólio encontrado para o usuário ID {}", userId);
        } else {
            log.info("Nenhum portfólio encontrado para o usuário ID {}", userId);
            throw new PortfolioNotFoundException("Portfólio não encontrado para o usuário com ID: " + userId);
        }

        return portfolio;
    }


    @Override
    public List<Investment> getAllInvestmentsByPortfolio(Long userId, Long portfolioId) throws UserNotFoundException, PortfolioNotFoundException {
        Portfolio portfolio = getPortfolioByUserAndPortfolioId(userId, portfolioId);
        List<Investment> investments = portfolio.getInvestments().stream().toList();
        log.info("Recuperados {} investimentos para o portfólio ID {} do usuário ID {}", investments.size(), portfolioId, userId);
        return investments;
    }

    /**
     * Método auxiliar para recuperar um portfólio por usuário e ID do portfólio.
     *
     * @param userId ID do usuário.
     * @param portfolioId ID do portfólio.
     * @return Portfolio encontrado.
     * @throws UserNotFoundException Se o usuário não for encontrado.
     * @throws PortfolioNotFoundException Se o portfólio não for encontrado.
     */
    private Portfolio getPortfolioByUserAndPortfolioId(Long userId, Long portfolioId) throws UserNotFoundException, PortfolioNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("Usuário não encontrado ao acessar portfólio: ID {}", userId);
                    return new UserNotFoundException("Usuário não encontrado com ID: " + userId);
                });
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> {
                    log.warn("Portfólio não encontrado: ID {}", portfolioId);
                    return new PortfolioNotFoundException("Portfólio não encontrado com ID: " + portfolioId);
                });
        if (!portfolio.getUser().getId().equals(userId)) {
            log.warn("Portfólio ID {} não pertence ao usuário ID {}", portfolioId, userId);
            throw new PortfolioNotFoundException("Portfólio não encontrado para o usuário com ID: " + userId);
        }
        return portfolio;
    }
}
