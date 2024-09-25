package com.InvestmentsTracker.investment_portfolio.config;

import com.InvestmentsTracker.investment_portfolio.model.Role;
import com.InvestmentsTracker.investment_portfolio.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Autowired
    private RoleRepository roleRepository;

    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (roleRepository.findByName("ROLE_USER") == null) {
            roleRepository.save(new Role("ROLE_USER"));
        }
        if (roleRepository.findByName("ROLE_ADMIN") == null) {
            roleRepository.save(new Role("ROLE_ADMIN"));
        }
    }
}
