package com.InvestmentsTracker.investment_portfolio.controller;

import com.InvestmentsTracker.investment_portfolio.model.User;
import com.InvestmentsTracker.investment_portfolio.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

/**
 * Controlador para gerenciar operações de usuários.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint para adicionar um novo usuário.
     *
     * @param user Objeto User a ser adicionado.
     * @return User criado ou mensagem de erro.
     */
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try {
            User savedUser = userService.addUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao adicionar usuário.");
        }
    }

    /**
     * Endpoint para atualizar um usuário existente.
     *
     * @param userId ID do usuário a ser atualizado.
     * @param user Objeto User com os dados atualizados.
     * @return User atualizado ou mensagem de erro.
     */
    @PutMapping("/{userId}/update")
    public ResponseEntity<?> updateUser(
            @PathVariable UUID userId,
            @RequestBody User user) {
        if (!userId.equals(user.getId())) {
            return ResponseEntity.badRequest().body("ID do caminho não corresponde ao ID do corpo.");
        }
        try {
            User updatedUser = userService.updateUser(user);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar usuário.");
        }
    }

    /**
     * Endpoint para remover um usuário específico.
     *
     * @param userId ID do usuário a ser removido.
     * @return Mensagem de sucesso ou erro.
     */
    @DeleteMapping("/{userId}/remove")
    public ResponseEntity<String> removeUser(@PathVariable UUID userId) {
        try {
            userService.removeUser(userId);
            return ResponseEntity.ok("Usuário removido com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao remover usuário.");
        }
    }

    /**
     * Endpoint para recuperar um usuário por ID.
     *
     * @param userId ID do usuário.
     * @return User encontrado ou mensagem de erro.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable UUID userId) {
        try {
            User user = userService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint para recuperar todos os usuários.
     *
     * @return Lista de usuários ou mensagem de erro.
     */
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao recuperar usuários.");
        }
    }
}
