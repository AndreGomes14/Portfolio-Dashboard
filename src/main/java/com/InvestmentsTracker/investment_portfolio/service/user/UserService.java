package com.InvestmentsTracker.investment_portfolio.service.user;

import com.InvestmentsTracker.investment_portfolio.model.User;

import java.util.List;
import java.util.UUID;

/**
 * Interface para serviços de gerenciamento de usuários.
 */
public interface UserService {

    /**
     * Adiciona um novo usuário.
     *
     * @param user Instância de User a ser adicionada.
     * @return User criado.
     */
    User addUser(User user);

    /**
     * Recupera um usuário por ID.
     *
     * @param userId ID do usuário.
     * @return User encontrado.
     */
    User getUserById(UUID userId);

    /**
     * Atualiza um usuário existente.
     *
     * @param user Instância de User a ser atualizada.
     * @return User atualizado.
     */
    User updateUser(User user);

    /**
     * Remove um usuário específico.
     *
     * @param userId ID do usuário a ser removido.
     */
    void removeUser(UUID userId);

    /**
     * Recupera todos os usuários.
     *
     * @return Lista de usuários.
     */
    List<User> getAllUsers();
}
