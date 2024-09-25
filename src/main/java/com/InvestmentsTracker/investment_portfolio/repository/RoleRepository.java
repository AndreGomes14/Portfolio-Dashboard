package com.InvestmentsTracker.investment_portfolio.repository;

import com.InvestmentsTracker.investment_portfolio.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}