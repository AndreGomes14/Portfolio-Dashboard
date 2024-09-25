package com.InvestmentsTracker.investment_portfolio.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class InvestmentValueHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Investment investment;

    private LocalDateTime timestamp;
    private double value;
}
