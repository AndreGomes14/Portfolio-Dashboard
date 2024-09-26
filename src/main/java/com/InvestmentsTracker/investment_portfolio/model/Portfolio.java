package com.InvestmentsTracker.investment_portfolio.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Slf4j
public class Portfolio {
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Investment> investments;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double totalValue = calculateTotalValue();
    private String name;

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
