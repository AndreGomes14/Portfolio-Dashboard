package com.InvestmentsTracker.investment_portfolio.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "users") // 'user' is a reserved keyword in some databases
public class User implements UserDetails {

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Portfolio portfolio;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User's login credentials
    @Column(unique = true, nullable = false)
    @NotBlank(message = "Username is required")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "Password is required")
    private String password; // Ensure this is stored securely (hashed)

    @Email(message = "Please provide a valid email")
    private String email;

    private boolean enabled = true;

    // Roles assigned to the user
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private Set<Role> roles = new HashSet<>();

    // Constructors
    public User() {}

    public User(String username, String password, String email, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    // Implementing UserDetails interface methods

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Map the roles to SimpleGrantedAuthority objects
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    // Account status flags

    @Override
    public boolean isAccountNonExpired() {
        return true; // Modify based on your application's logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Modify based on your application's logic
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Modify based on your application's logic
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
