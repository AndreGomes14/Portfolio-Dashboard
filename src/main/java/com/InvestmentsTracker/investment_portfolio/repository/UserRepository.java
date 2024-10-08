package com.InvestmentsTracker.investment_portfolio.repository;

import com.InvestmentsTracker.investment_portfolio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Encontra um usuário pelo email.
     *
     * @param email Email do usuário.
     * @return Optional contendo o usuário, se encontrado.
     */
    Optional<User> findByEmail(String email);
}
