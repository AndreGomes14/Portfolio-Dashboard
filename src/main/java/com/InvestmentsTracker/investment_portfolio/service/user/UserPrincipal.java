package com.InvestmentsTracker.investment_portfolio.service.user;

import com.InvestmentsTracker.investment_portfolio.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class UserPrincipal implements UserDetails {

    private Long id;
    private String name;
    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    private UserPrincipal(Long id, String name, String email, String password,
                          Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * Cria uma instância de UserPrincipal a partir de um objeto User.
     *
     * @param user Objeto User.
     * @return Instância de UserPrincipal.
     */
    public static UserPrincipal create(User user) {
        // Por simplicidade, estamos atribuindo uma autoridade fixa.
        // Em um cenário real, você pode carregar as autoridades do usuário.
        return new UserPrincipal(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList()
        );
    }

    @Override
    public String getUsername() {
        return email;
    }

    // Métodos adicionais do UserDetails
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
