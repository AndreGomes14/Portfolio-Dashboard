package com.InvestmentsTracker.investment_portfolio.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity // Make sure it's marked as a JPA entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Investment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double buyPrice;
    private double units;
    private LocalDate date;

    @Transient
    private double amountInvested;

    @Setter
    @Transient
    private double currentValue;

    @ManyToOne
    @JoinColumn(name = "portfolio_id") // Foreign key to Portfolio table
    private Portfolio portfolio;
    public double calculateTotalValue() {
        return buyPrice * units;
    }
    public double getAmountInvested() {
        return buyPrice * units;
    }
    public abstract double getCurrentMarketPrice();

    public double getCurrentValue() {
        return getCurrentMarketPrice();
    }

    @Override
    public String toString() {
        return "Investment{" +
                "name='" + name + '\'' +
                ", buyPrice=" + buyPrice +
                ", units=" + units +
                '}';
    }

}
