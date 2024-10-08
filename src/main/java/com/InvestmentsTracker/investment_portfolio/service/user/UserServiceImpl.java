package com.InvestmentsTracker.investment_portfolio.service.user;

import com.InvestmentsTracker.investment_portfolio.model.User;
import com.InvestmentsTracker.investment_portfolio.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de usuários.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(User user) {
        User savedUser = userRepository.save(user);
        log.info("Novo usuário adicionado: {}", savedUser);
        return savedUser;
    }

    @Override
    public User getUserById(UUID userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            log.info("Usuário recuperado: {}", userOpt.get());
            return userOpt.get();
        } else {
            log.warn("Usuário não encontrado: ID {}", userId);
            throw new IllegalArgumentException("Usuário não encontrado com ID: " + userId);
        }
    }

    @Override
    public User updateUser(User user) {
        if (user.getId() == null || !userRepository.existsById(user.getId())) {
            log.warn("Tentativa de atualizar usuário inexistente: ID {}", user.getId());
            throw new IllegalArgumentException("Usuário não encontrado com ID: " + user.getId());
        }
        User updatedUser = userRepository.save(user);
        log.info("Usuário atualizado: {}", updatedUser);
        return updatedUser;
    }

    @Override
    public void removeUser(UUID userId) {
        if (!userRepository.existsById(userId)) {
            log.warn("Tentativa de remover usuário inexistente: ID {}", userId);
            throw new IllegalArgumentException("Usuário não encontrado com ID: " + userId);
        }
        userRepository.deleteById(userId);
        log.info("Usuário removido: ID {}", userId);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        log.info("Recuperados {} usuários.", users.size());
        return users;
    }
}
