package com.InvestmentsTracker.investment_portfolio.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    // Role name, e.g., "ROLE_USER", "ROLE_ADMIN"
    @Column(unique = true, nullable = false)
    private String name;

    // Default constructor required by JPA
    public Role() {
    }

    // Constructor with name parameter
    public Role(String name) {
        this.name = name;
    }

    // Optionally, override equals and hashCode if necessary
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
