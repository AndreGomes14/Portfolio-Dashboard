package com.InvestmentsTracker.investment_portfolio.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Slf4j
@Entity
public class Portfolio {
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private double totalValue = calculateTotalValue();

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Investment> investments;

    // Constructor
    public Portfolio() {
        this.investments = new ArrayList<>();
        this.name = name;
    }
    public void addInvestment(Investment investment) {
        investments.add(investment);
    }

    // Remove an investment from the portfolio
    public void removeInvestment(Investment investment) {
        investments.remove(investment);
    }

    public double calculateTotalValue() {
        assert investments != null;
        return investments.stream().mapToDouble(Investment::calculateTotalValue).sum();
    }
    public void printPortfolioSummary() {
        System.out.println("Portfolio of " + user.getUsername() + ":");
        for (Investment investment : investments) {
            System.out.println(investment.getInvestmentType() + ": " + investment.calculateTotalValue());
        }
        System.out.println("Total Portfolio Value: " + calculateTotalValue());
    }

}
